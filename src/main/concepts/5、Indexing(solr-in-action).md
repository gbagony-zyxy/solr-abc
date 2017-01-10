### This chapter covers:
```
1、Designing your schema for indexing documents
2、Defining field and field types in schema.xml
3、Using field types for structured data
4、Handling update requests,commits and atomic updates
5、Managing index settings in solrconfig.xml
```


```
Inverted Index(倒序索引，Lucene索引核心)

Processes documents to build the index --> key factor:text analysis


```

##### 1、Example microblog search application
###### 1.1、Representing content for searching

Field | Value
---|---
id | 1
screen_name | @ruyin
timestamp | 2017-01-10
lang | en
user_id | 999999999
favorites_count | 10
text | It's not easy!

###### 1.2、Overview of the Solr indexing process
```
At a high level,the Solr indexing process distills(提取) down to three key tasks:
(1)Convert a cocument from its native format into a format supported by Solr,such as XML or JSON.
(2)Add the document to Solr using one of several well-defined interfaces,typically HTTP POST.
(3)Configure Solr to apply transformations to the text int the document during indexing.
```

##### 2、Designing your schema
```
Learn to answer the follwing key questions about your search application:
(1)What us a document in your index?
(2)How is each document uniquely identified?
(3)What fields in your documents canbe searched by users?
(4)Which fields should be displayed to users in the search result?
```

###### 2.1、Document granularity(文档粒度)
###### 2.2、Unique key
###### 2.3、Indexed fields
```
The best way to think about indexed fields is to ask whether a typical user could develop a meaningful query using that field.

Inaddition to enabling searching,you will also need to mark your field as indexed if you need to sort,facet,group
by,provide query suggestions for or execute function queries on values within a field.With certain advanced settings
enabled,marking fields as indexed can also be useful for speeding up hit highlighting.

Toke a moment to think about the indexed fields for your documents,keep these fresh in your mind.
```

###### 2.4、Stored fields
```
In general,your documents may contain fields that aren't useful from a search perspective but are still useful for displaying
search result.These are called stored fields.

As a search-application architect,one of your goals should be to minimize the size of your index.
```

###### 2.5、Preview of managed-schema
```
FileType elements govern if and how a field is analyzed.
A fieldType configuration useful for analyzing general text.

Three main sectoins of the managed-schema document:
(1)、The <fields> element,containing <field> and <dynamicField> elements used to define the basic structure of your documents.
(2)、Miscellaneous elements,such as <uniqueKey> and <copyField>,which are listed after the <fields> elements.
(3)、Field types under the <types> element that determine how dates,numbers and text fields are handled in Solr.
```

##### 3、Defining fields in managed-schema
###### 3.1、Required field attributes
```
One thing that can be confusing is that when a field is stored,Solr stores the original value and not the analyzed value.
```
###### 3.2、Multivalued fields
```
允许一个field可包含多个值，类似于list
```

###### 3.3、Dynamic fields
```
In Solr,dynamic fields allow you to apply the same definition to any fields in your documents whose names match either a
prefix or suffix pattern.

Dynamic fields help address common problems that occur when building search applications,including:
(1)Modeling documents with many fields
(2)Supporting documents from diverse sources
(3)Adding new document sources

- Modeling documents with many fields:
Dynamic fields help you model documents having many fields by alloing you to match a prefix or suffix pattern,applying the
same field definition in managed-schema to any matching fields.

Dynamic fields therefore help save typing and simplify your managed-schema when you have many fields.

- Supporting documents from diverse sources:
Another benefit of dynamic fields is that they help you support a mixture of documents that share a commom base
schema,but also have some unique fields.

- Adding new document sources:
With dynamic fields,you can include new fields introduced by your new document source without making any changes to the
managed-schema.
When querying for ducuments that were indexed with dynamic fields,you must use the full field name in the query.
```

###### 3.4、Copy fields
```
In Solr, copy fields allow you to populate one field from one or more other fields.Specifically,copy fields support two use
cases that are commom in most search applications:
(1)Populate a single catch-all field with the contents of multiple fields.
(2)Apply different text analysis to the same field content to create a new searchable field.

- Create a catch-all field from many fields:
Solr makes it easy to create a single catch-all search field from many other fields in your document using the <copyField>
directive.

First,you need to define a destination field that other fields will be copied into:
<field name="catch-all"
    type="text_cn"
    indexed="true"
    stored="false"
    multiValued="true"/>
There are two important aspects to the definition:
(1)The catch-all field shouldn't be stored as it's populated from another field.(Remember that Solr returns the original
value for a stored field)
(2)Destination field must be multiValued if any of the source fields are multivalued.(You must set multiValued="true" if
you copy more than one source field into the destination field,even if all the source fields are single valued.)

Note that the <copyField> element is a sibling of the <fields> and <types> elements in managed-schema.Take a moment
to think about why this is the case.


- Apply different analyzers to a field:
Stemming is a technique that transforms terms into a common base form,known as a stem.

Consider the following listing from managed-schema:
<field name="text"
    type="stemmed_text"
    indexed="true"
    stored="true"/>
<field name="auto_suggest"
    type="unstemmed_text"
    indexed="true"
    stored="false"
    multiValue="true"/>
<copyField source="text" dest="auto_suggest"/>
Raw text content from the text field copied into the auto_suggest field to be analyzed using the unstemmed_text
field type.

```

###### 3.5 Unique ley field
```
If you provide a unique identifier field for each of your documents,Solr will avoid creating duplicates during indexing.
One thing to note is that it's best to use a primitive type,such as string or long,for the field you indicate as
being the <uniqueKey/> as that ensures Solr doesn't make any changes to the value during indexing.
```

##### 4、Field types for structured nontext fields
###### 4.1、String fields
```
Solr provides the string field type for fields that contain structured values that shouldn't be alter in anyway.
<fieldType name="string" class="solr.StrField"
                sortMissingLast="true" 
                omitNorms="true"/>

```

###### 4.2、Date fields
```
Solr provides an optimized build-in <fieldType> called tdate,shown next:
<fieldType name="tdate" class="solr.TrieDateField"
                omitNorms="true"
                precisionStep="6"
                positionIncrementGap="0"/>

A trie is an advanced tree-based data structure that allows for efficient searching for numeric and date values by
varying degrees of precision.

- Date granularity:
<field name="teimestamp">2017-01-10T15:44:22Z/HOUR</field>
The "/" tells Solr to "round down" to a specific granularity.

To query for all documents from today ,you could do timestamp:[NOW/DAY TO NOW/DAY+1DAY]。
```
###### 4.3、Numeric fields
```
This isn't an intuitive field to search by,but it's useful from a display-and-sorting perspective.

Note that you shouldn't index a numeric field that you need to sort as a string field because Solr will do a lexical sort
instead of a numberic sort if the underlying type is stringbased.
实际应用中不应该对类型为string的字段进行sort，因为排序是按照词汇的ASCII大小排的，会出现2在10后面的情况。
```

###### 4.4、Advanced field type attributes
```
Solr supports optional attributes for field typesto enable advanced behavior:
```
Attribute | Behavior when enabled (="true")
---|---
sortMissingFirst | 排序时没有该项值放在首位
sortMissingLast | 排序时没有该项值放在最后
precisionStep | 字段的精确度(数值与)
positionIncrementGap | 字段之后空格数
```
<fieldType name="tint" class="solr.TrieIntField"
                    precisionStep="8"
                    positionIncrementGap="0"/>
```

Percision step(8) | Operation | Indexed term
---|---|---
0:no bits removed | 327500 & 0xFFFFFFFF | 327500
1:8 least significant bits removed | 327500 & 0xFFFFFF00 | 327424
2:16 least significant bits removed | 327500 & 0xFFFF0000 | 262144
3:24 least significant bits removed | 327500 & 0xFF000000 | 0
```
The table shows that at each precision step,Lucene removes(8*step count)least significant bits from the original
value,which reduces the precision of the indexed term.
```

##### 5、Sending documents to Solr for indexing
###### 5.1、Indexing documents using XML or JSON
```
java -jar post.jar xxx.xml
java -Dtype=application/json -jar post.jar xxx.json

Solr will update an existing document using the unique key field.
```
###### 5.2、Using the SolrJ client library to add documents from Java


##### 6、Update handler
```
Overview of common requests processed by the update handler
```

Request type | Description | XML example
---|---|---
Add | 将doc添加到索引中 | <add><doc>...</doc></add>
Delete | 根据主键删除doc | <delete><id>...</id></delete>
Delete by query | 删除查询到的结果集 | <delete><query>...<query></delete>
Atomic update | 使用优化锁更新已存在的doc | <add><doc><field>...</field></doc></add>
Commit | 提交doc到索引中 | <commit waitSearcher="true" softCommit="false"/>
Optimize | 通过合并片段空间以及回收删除数据来优化索引 | <opyimize waitSearcher="false"/>

```
The listing shows the configuration of the update handler in solrconfig.xml

<updateHandler class="solr.DirectUpdateHandler2">
    <updateLog>
        <str name="dir">${solr.ulog.dir:}</str>
    </updateLog>
    <autoCommit>
        <maxTime>15000</maxTime>
        <openSearcher>false</openSearcher>
    </autoCommit>
    <autoSoftCommit>
        <maxTime>1000</maxTime>
    </autoSoftCommit>
    <listener event="postCommit" ...>
    ...
    </listener>
</updateHandler>
```

