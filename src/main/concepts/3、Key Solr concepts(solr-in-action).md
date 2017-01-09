##### 1、Searching,matching and finding content   
###### 1.3、The inverted index 反向索引
```
* Two final important details about the inverted index:
* All terms in the index map to the one or more documents
* Terms in the inverted index are sorted in ascending
lexicographical order 
```

 
###### 1.4、Terms、phrase and Boolean logic
```
Required terms: AND(+)  
Optional terms: OR(+)   
Negated terms:  NOT(-)    
Phrase:  可联合查询
Grouped expression: 
```

###### 1.5、Finding sets of documents
```
找到对应集合(AND,OR,NOT)
```

###### 1.6、Phrase queries and term positions
```
字段查询以及项位置
```


###### 1.7、Fuzzy matching 模糊查询
```
* Wildcard searching:通配符搜索
* Range searching: 范围搜索 [ TO ] { TO } [ TO }
* Edit-distance searching: 编辑范围搜索 administrator~ (administrator,administrater,administratior..etc)
* Proximity searching:  近似搜索
```

##### 2、Relevancy    
###### 2.1、Default similarity
```
Solr's relevancy score are based upon the Similarity class,which can be defined on a per-field basis in Solr's schema.xml. 
The important concepts in the relevancy calculation:
* term frequency(tf) 词的频率
* inverse document frequency(idf) 关键词的概率
* term boosts(t.getBoost)  增强你所认为重要的部分  
* field normalization(norm)     
* coordination factor(coord)
* query normalization(queryNorm)

Term frequency
Inverse document frequency
Boosting
Normalization factors:field norms,query norms and the coord factor  
(1)Fied Norms  doc boost表示doc被添加到solr的次数，field boost表示字段出现的频率，length boost表示doc的长度
Field norms are calculated at index time and are represented as an additional byte per field in the Solr index.
(2)Query Norms
The query norm should not affect the relative weighting of each document that matchess a given query.
(3)The Coord Factor
Its role is to measure how much of the query each document matches.衡量一个查询有多少doc匹配
```

##### 3、Precision and Recall 精度和查全率(占比)
###### 3.1 Precision   满足查询条件的结果
```
#Correct Matches / #Total Results Returned  
```
###### 3.2 Recall  查询的结果集(返回结果集中正确的比率)
```
#Correct Matches / (#Correct Matches + #Missed Matches)
```
###### 3.3 Striking the right balance
```
Measuring for Recall across the entire result set and measuring for Precision only within the first page of search result.
```

###### 3.4 Searching at scale
```
(1)The denormalized document  包含必要信息，并行处理数据，支持亿万级别的访问
(2)Distributed searching
```