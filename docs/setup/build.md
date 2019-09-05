注：本实例参考《重新定义SpringCloud实战》一书中的tcc模块 demo，在此基础上，将h2+jpa数据库，改为使用mysql+mybatis 实现。

代码拉取实现：
1.初始化数据库及sql语句
执行：

```
tcc_inventory.sql
tcc_order.sql
```

2.依次启动 tcc-coordinator-atomikos ，inventory-service， order-service 模块。
3. 在 tcc-coordinator-customer 的 TccCoordinatorCustomerApplicationTests 中执行UT：可模拟各场景。

关于TCC模式的分布式事务相关概念：
定义  
2007年Amazon的Pat Helland在Conference on Innovative Database Research上发表了一篇文章-( Life Beyond Distributed Transactions:An apostate's opinion ),首次提出了解决分布式事务一致性的Tentative Operations, Confirmation和Cancellation模型。10年后的2017年Pat Helland在acmqueue上发表了一篇同名文章,对前一篇文章的内容进行了概括及更新。其基本定义如下:
- Tentative Operation :为了在多个实体之间达成一致,要求一个实体必须能够接受另一个实体对请求执行的不确定性,比如,在发出执行操作请求之后又发出取消该操作的请求。这类后续可能请求取消的操作,就称为Tentative Operation
-  Confirmation:如果请求方认为Tentative Operation没问题,那么就可以发出Confirmation的执行请求,最终确定这个操作。
- Cancellation :如果请求方决定撤回Tentative Operation,那么就发出Cancellation的执行请求,取消这个操作。

当一个实体统一执行Tentative Operation的时候,就意味着它接受了这种不确定性,允许另外的实体通过发出Confirmation或者Cancellation请求,来降低这种不确定性,最终完成这个Tentative Operation。

Gregor Hohpe在其维护的www.enterpriseintegrationpatterns.com网站中,将Pat Helland提出的Tentative Operations, Confirmation和Cancellation模型概括为Tentative Operation模式(http://www.enterpriseintegrationpatterns.com/patterns/conversation/TryConfirmCancel.html),然后提出与之类似的TCC (Try-Confirm-Cancel)事务模型,其状态图如图24-4所示

![image](59867404A7764248801EFA6CC74FEDFC)

TCC事务模型总共有nitial, Reserved, Final三个状态:
-  Initial状态:是最初始的状态,接到Try请求时变成Reserved状态。
-  Reserved状态:接收Confim请求时变成Final状态,如果接收Cancel请求或者是等待超时则退回到Initial状态。
- Final状态：TCC事务成功状态。