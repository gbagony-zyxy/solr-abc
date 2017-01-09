
```
1、Overview of solrconfig.xml
2、Query request handling
3、Managing searchers
4、Cache management
4、Remaining configuration options

1、Handling a query request     
2、Extending query processing with search components    
3、Manageing and warming searchers  
4、Managing Cache behavior

* solr.xml
* solrconfig.xml
* managed-schema

What's important is to understand that Solr can autodiscover cores during startup using core.properties.
```

##### 1、Overview of solrconfig.xml       
###### 1.1、 Common XML data-structure and type elements       

Element | Description | Example
---|---|---
<arr> | Named,ordered array of objects | <arr name="last-component"><str>spellcheck</str></arr>
<lst> | Named,ordered list of name/value pairs | <lst name="default"><str name="omitHeader">true</str><str name="wt">json</str></lst>
<bool>| Boolean value | <bool>true</bool>
<str> | String value | <str name="wt">json</str>
<int> | Integer value | <int>100</int>
<long>| Long value | <long>1111111111111111</long>
<float>| Float value | <float>3.14</float>
<double>| Double value | <double>3.1415926</double>

###### 1.2、Applying configuration changes     
```
更改solrconfig.xml文件之后不必每次重启solr或者core，在管理界面core admin中可选择reload即可应用新的设置。
```
###### 1.3、Miscellanenous settings(各种各样的设置)
```
Lucene version：    
Loading dependency JAR files:   
Eanble JMX: 
```

##### 2、Query request handler
###### 2.1、Request-handling overview：
###### 2.2、Search handler
```
- 1、Request parameterdecoration，using
    - default--Set default parameters on the request if they are not explicitly provided by the client.
    - invariants--Set parameters to fixed values,which override values provided by the client.
    - appends--Additional parameters to be combined with the parameters provided by the client.

- 2、first-components--An optional chain of search components that are executed first to perform preprocessing tasks.
- 3、components--The primary chain of search components that must at least include the query component.
- 4、last-components--An optional chain if search components that are applied last to perform postprocessing tasks.


- Specifically, the defaults list provides two main benefits to your application:
   * It helps simplify client code by establishing sensible defaults for your application in one place.
   * By preconfiguring complex components like faceting,you can establish consistent behavior for all queries while keeping your client code simple.
```

###### 2.4、Extending query processing with search components
```
1、Query Component：
The query component is always enabled and all other components need to be explicitly ennabled using query parameters.

2、Facet Component:
Faceting is enabled using the default parameter:<str name="facet">true</str>

3、More Like This Component:

4、Highlight Component:

5、Stats Component:

6、Debug Component:

7、Adding Spellcheck as a last-component:

```

##### 3、Managing searches
###### 3.1、New searcher overview
```
A commit operation creates a new searcher to make new documents and updates visible.So the old searcher must be destroyed ,but
there are some problems when someone query currently executing against the old searcher,so solr must wait for all in progress
queries to complete.

The good news is that solr has a number of tools to help alleviate this situation.Fist and foremost,solr supports the
concept of warming a new searcher in the background and keeping the current searcher active until the new one is fully warmed.
```

###### 3.2、Warming a new searcher
```
dig into Solr's cache-management feature.

You are responsible for configuring your own warming queries.
```

```
- Choosing warming queries:
A rule of thumb(一个经验法则),warming queries should contain query-request parameters(q,fq,sort,etc)that are used frequently by your application.

If query performance begin to suffer after commits,then you'll know it's time to consider using warming queries.
```

```
- Too many warming queries:
It turns out that warming too many searchers in your application concurrently can consume too many resources(CPU and memory),
leading to to a degraded search experience.
```
```
- First searcher:
```
```
- Use cold searcher:
<useColdSearcher>false/true</useColdSearcher>是否使用未预热的searcher，如果为false，则Solr将会阻塞直到searcher全部预热完成，为true则会立刻启动
```
```
- MAX warming searcher：
<maxWarmingSearching>控制并发同时启动的searcher的最大数
```

##### 4、Cache management
###### 4.1、Cache fundamentals
```
There are four main concerns when working with Solr caches:
 (1)设置cache的容量以及cache回收的策略
 * Cache sizing and eviction policy(LRU or LFU)[Least Recently Used / Least Frequently Used]
 (2)点击率以及回收
 * Hit ratio and evictions
 (3)cache对象失效
 * Cached-object invalidation
 (4)自动预热新的cache
 * Autowarming new caches


- Cache sizing:
- Hit ratio and evictions:
The hit ratio indicates how much benifit your application is getting from its cache.
- Cached-object invalidation:
In solr,all objects in a cache are linked to a specific searcher instance and are immediately invalidated when a
searcher is closed.Recall that a searcher is a read-only view of a snapshot of your index;consequently, all cached objects
remain valid until the searcher is closed.
- Autowarming new caches:
Solr creates a new searcher after a commit,but it doesn't close the old searcher until the new searcher is fully warmed.
```
###### 4.2、Filter cache(one of the most important caches)：
```
When Solr executes query ,it computes and caches an efficient data structure that indicates which documents in your index match the filter.

Consider what happens if another query is sent to Solr with the same filter query(fq=name:wxz)but a different query,such
as q=wpl.Wouldn't it be nice if the second query could use the filter query the first query?

<filterCache class="solr.FastLRUCache"
                 size="512"
                 initialSize="512"
                 autowarmCount="0"/>

Using filters to optimize queries is one of the most powerful feature in Solr,mainly because filters are reusable across queries.


- Autowarming the filter cache:
Filters can be expensive to create and store in memory if you have a large number of documents in your index,or if the
filter criteria are complex.

If a filter is generic enough to apply to multiple queries in your application,it makes sense to cache the resulting
filter.In addition,you probably want to autowarm some of the cached filters when opening a new searcher.

We recommend that you enable autowarming for the filter cache,but set the autowarmCount attribute to a small number
to start.In addition, we think the LFU eviction policy is more appropriate for the filter cache because it allows you
to keep the filter cache small and give priority to the most popular filter in your application.
```

###### 4.3、Query result cache
```
- Query result window size
The <queryResultWindowSize> element allows you to prepare additional pages when you execute a query.
想象你每页显示10条，而你经常只会查看前两页的内容，故你可以设置<queryResultWindowSize>为20,那么当你看第二页内容就不必再次
重新查询。

<queryResultCache class="solr.LRUCache"
                size="512"
                initialSize="512"
                autowarmCount="0"/>

- Query result max docs cached:
The <queryResultMaxDocsCached> element allows you to limit the number of documents cached for each entry in the query
result cache.

- Enable lazy field loading:
A common design pattern in Solr is to have a query return a subset of fields for each document.
<enableLazyFieldLoading>如果为true，则在查询doc时，如果只是查询doc的一部分字段，则只显示该部分字段，其他的字段懒加载。
```

###### 4.4、Document cache
```

```

###### 4.5、Field value cache
```
Field value which is strictly used by Lucene and is not managed by Solr.It is used during sorting and when building documents for the response.

How Solr processes queries using a request-handling pipeline,and you know how to optimize query performance using
new-searcher warming and caching.
```


##### 5、Summary
```
At this point you should have a solid understanding of how to configure Solr,particularly for optimizing query-processing
performance.

There is only one active searcher in Solr at any time,and a new searcher needs to be created before updates to the index are visible.

- How Solr processes query requests using a read-only view of the index with a component called a searcher.
- Solr also provides a number of important caches that need to be fine-tuned for your application
(filter/query result/document/field value).
- Caches can be warmed when creating a new searcher,which also helps optimize query performaance.
```