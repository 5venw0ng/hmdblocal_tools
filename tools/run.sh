#!/bin/bash
# 快捷启动，执行方式为：
# ./run.sh start|stop|restart dev|prod|test
# 参数1是命令，启动、停止或者重启
# 参数2是运行环境，只有start、restart才有用，默认是dev=开发环境

AppName=biotools-admin.jar

#JVM参数
JVM_OPTS="-Dname=$AppName  -Duser.timezone=Asia/Shanghai -Xms1024m -Xmx4096m -Xmn256m -XX:+HeapDumpOnOutOfMemoryError -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseG1GC -XX:MetaspaceSize=512m"
APP_HOME=`pwd`

#环境参数
RUN_ENV="dev"

if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|release|status} \033[0m"
    exit 1
fi


if [ "$2" != "" ];then
  RUN_ENV="$2"
fi

if [ "$AppName" = "" ];
then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

function compile(){
  #退出到根目录
  cd ../
  git pull
  mvn clean
  mvn package

  #推出到根目录的上级
  #cd ../
  #复制打包好的jar文件
  cp -r ./biotools-admin/target/$AppName ./
  #再回到tools目录
  cd ./tools
}

function start()
{
  echo "Current env $RUN_ENV"
  PID=`ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}'`

	if [ x"$PID" != x"" ]; then
	    echo "$AppName is running..."
	else
		#nohup java -jar  $JVM_OPTS target/$AppName > /dev/null 2>&1 &
		cd ../
		nohup java -jar $JVM_OPTS $AppName --spring.profiles.active=$RUN_ENV &
		echo "Start $AppName success..."
	fi
}

function stop()
{
  echo "Stop $AppName"

	PID=""
	query(){
		PID=`ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}'`
	}

	query
	if [ x"$PID" != x"" ]; then
		kill -TERM $PID
		echo "$AppName (pid:$PID) exiting..."
		while [ x"$PID" != x"" ]
		do
			sleep 1
			query
		done
		echo "$AppName exited."
	else
		echo "$AppName already stopped."
	fi
}

#发布，会重新编译
function release(){
    compile
    stop
    sleep 2
    start
}


#重启，仅仅是重启
function restart()
{
    stop
    sleep 2
    start
}

function status()
{
    PID=`ps -ef |grep java|grep $AppName|grep -v grep|wc -l`
    if [ $PID != 0 ];then
        echo "$AppName is running..."
    else
        echo "$AppName is not running..."
    fi
}

case $1 in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    status)
    status;;
    release)
    release;;
    *)

esac
