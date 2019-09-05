# tcc-rest
SpringCloud 的分布式事务处理方案-TCC模式

版权声明：本实例参考《重新定义SpringCloud实战》一书中的tcc模块 demo，在此基础上，将h2+jpa数据库，改为使用mysql+mybatis 实现。

代码拉取实现：
1.初始化数据库及sql语句
执行：

```
tcc_inventory.sql
tcc_order.sql
```

2.依次启动 tcc-coordinator-atomikos ，inventory-service， order-service 模块。
3. 在 tcc-coordinator-customer 的 TccCoordinatorCustomerApplicationTests 中执行UT：可模拟各场景。