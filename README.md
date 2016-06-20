Volcano Planner Test
====================

Test calcite volcano planner optimization.

Logical Plan Before Optimization
--------------------------------

```
LogicalDelta
  LogicalProject(orderid=[$0])
    LogicalFilter(condition=[>($1, 3)])
      EnumerableTableScan(table=[[ORDERS]])
```

Volcano planner return following errors
---------------------------------------

```
org.apache.calcite.plan.RelOptPlanner$CannotPlanException: Node [rel#15:Subset#3.BINDABLE.[]] could not be implemented; planner state:

Root: rel#15:Subset#3.BINDABLE.[]
Original rel:

Sets:
Set#0, type: RecordType(INTEGER orderid, INTEGER productid)
	rel#8:Subset#0.ENUMERABLE.[], best=rel#4, importance=0.6561
		rel#4:EnumerableTableScan.ENUMERABLE.[](table=[ORDERS]), rowcount=100.0, cumulative cost={100.0 rows, 101.0 cpu, 0.0 io}
	rel#54:Subset#0.BINDABLE.[], best=null, importance=0.32805
Set#1, type: RecordType(INTEGER orderid, INTEGER productid)
	rel#10:Subset#1.NONE.[], best=null, importance=0.7290000000000001
		rel#9:LogicalFilter.NONE.[](input=rel#8:Subset#0.ENUMERABLE.[],condition=>($1, 3)), rowcount=50.0, cumulative cost={inf}
	rel#41:Subset#1.BINDABLE.[], best=null, importance=0.49499999999999994
		rel#40:InterpretableConverter.BINDABLE.[](input=rel#10:Subset#1.NONE.[]), rowcount=50.0, cumulative cost={inf}
		rel#55:BindableFilter.BINDABLE.[[]](input=rel#54:Subset#0.BINDABLE.[],condition=>($1, 3)), rowcount=50.0, cumulative cost={inf}
Set#2, type: RecordType(INTEGER orderid)
	rel#12:Subset#2.NONE.[], best=null, importance=0.81
		rel#11:LogicalProject.NONE.[](input=rel#10:Subset#1.NONE.[],orderid=$0), rowcount=50.0, cumulative cost={inf}
	rel#39:Subset#2.BINDABLE.[], best=null, importance=0.405
		rel#38:InterpretableConverter.BINDABLE.[](input=rel#12:Subset#2.NONE.[]), rowcount=50.0, cumulative cost={inf}
		rel#53:BindableProject.BINDABLE.[](input=rel#41:Subset#1.BINDABLE.[],orderid=$0), rowcount=50.0, cumulative cost={inf}
Set#3, type: RecordType(INTEGER orderid)
	rel#14:Subset#3.NONE.[], best=null, importance=0.9
		rel#13:LogicalDelta.NONE.[](input=rel#12:Subset#2.NONE.[]), rowcount=50.0, cumulative cost={inf}
		rel#21:LogicalProject.NONE.[](input=rel#20:Subset#4.NONE.[],orderid=$0), rowcount=50.0, cumulative cost={inf}
	rel#15:Subset#3.BINDABLE.[], best=null, importance=1.0
		rel#16:AbstractConverter.BINDABLE.[](input=rel#14:Subset#3.NONE.[],convention=BINDABLE,sort=[]), rowcount=50.0, cumulative cost={inf}
		rel#17:InterpretableConverter.BINDABLE.[](input=rel#14:Subset#3.NONE.[]), rowcount=50.0, cumulative cost={inf}
		rel#27:BindableProject.BINDABLE.[](input=rel#26:Subset#4.BINDABLE.[],orderid=$0), rowcount=50.0, cumulative cost={inf}
Set#4, type: RecordType(INTEGER orderid, INTEGER productid)
	rel#20:Subset#4.NONE.[], best=null, importance=0.81
		rel#18:LogicalDelta.NONE.[](input=rel#10:Subset#1.NONE.[]), rowcount=50.0, cumulative cost={inf}
		rel#31:LogicalFilter.NONE.[](input=rel#30:Subset#5.NONE.[],condition=>($1, 3)), rowcount=50.0, cumulative cost={inf}
	rel#26:Subset#4.BINDABLE.[], best=null, importance=0.9
		rel#33:InterpretableConverter.BINDABLE.[](input=rel#20:Subset#4.NONE.[]), rowcount=50.0, cumulative cost={inf}
		rel#43:BindableFilter.BINDABLE.[[]](input=rel#42:Subset#5.BINDABLE.[],condition=>($1, 3)), rowcount=50.0, cumulative cost={inf}
Set#5, type: RecordType(INTEGER orderid, INTEGER productid)
	rel#30:Subset#5.NONE.[], best=null, importance=0.7290000000000001
		rel#28:LogicalDelta.NONE.[](input=rel#8:Subset#0.ENUMERABLE.[]), rowcount=100.0, cumulative cost={inf}
		rel#37:LogicalTableScan.NONE.[](table=[]), rowcount=100.0, cumulative cost={inf}
	rel#42:Subset#5.BINDABLE.[], best=null, importance=0.81
		rel#47:InterpretableConverter.BINDABLE.[](input=rel#30:Subset#5.NONE.[]), rowcount=100.0, cumulative cost={inf}



	at org.apache.calcite.plan.volcano.RelSubset$CheapestPlanReplacer.visit(RelSubset.java:443)
	at org.apache.calcite.plan.volcano.RelSubset.buildCheapestPlan(RelSubset.java:293)
	at org.apache.calcite.plan.volcano.VolcanoPlanner.findBestExp(VolcanoPlanner.java:841)
	at org.apache.calcite.tools.Programs$RuleSetProgram.run(Programs.java:334)
	at org.apache.calcite.prepare.PlannerImpl.transform(PlannerImpl.java:290)
	at org.yee.plan.QueryPlanner.getPlan(QueryPlanner.java:59)
	at org.yee.plan.QueryPlannerTest.getPlan(QueryPlannerTest.java:39)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:119)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:42)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:234)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:74)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
```