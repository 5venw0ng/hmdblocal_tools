#!/usr/bin/python3

import xml.etree.ElementTree as ET
import psutil
import os
import xmltodict, json
from MongoDBUtil import MongoDBUtil

namespace = '{http://www.hmdb.ca}';
def parse_and_remove(filename, groupTag):
    '''
        增量解析 filename ，并且按groupTag作为一组
    '''
    ET.register_namespace("xmlns","http://www.hmdb.ca")
    #迭代文件，增量
    doc = ET.iterparse(filename, ('start', 'end'))
    # Skip the root element
    next(doc)
    
    #临时存储
    elem_stack = []
    for event, elem in doc:
        if event == 'end':
            if namespace+groupTag == elem.tag:
                yield elem
                elem.clear()
            try:
                elem_stack.pop()
            except IndexError:
                pass


if __name__ == "__main__":
    data = parse_and_remove('hmdb_l30000.xml', 'metabolite')

    mongoUtil = MongoDBUtil(ip="127.0.0.1", db_name="bio_db", port="27017")
    insertBatch = []
    idx = 0
    for meta in data:
        
        xmlstr_byte = ET.tostring(meta, encoding='utf8', method='xml')
        xmlstr = str( xmlstr_byte,encoding = "utf8" ).replace("xmlns:","");

        xmldict = xmltodict.parse(xmlstr) 
        accessionId = meta.findtext(namespace+"accession")
        
        #批量插入
        insertBatch.append(xmldict)
        idx+=1
        if(idx % 500 == 0):
            #500条插入一次
            mongoUtil.insert_many(collect_name="hmdb2210", documents=insertBatch)
            #插入后清理一下    
            insertBatch.clear()
            print("insert 500 data, clear")

    #如果循环执行完了，insertbatch中还有小于500的，那就执行完，因为我也不知道
    #怎么判断生成器是不是执行到了最后一个行
    if(len(insertBatch) < 500):
        mongoUtil.insert_many(collect_name="hmdb2210", documents=insertBatch)

    print('all data done.')