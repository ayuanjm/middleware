//编写一个 SQL 查询，获取 Employee 表中第二高的薪水（Salary） 。 
//
// +----+--------+
//| Id | Salary |
//+----+--------+
//| 1  | 100    |
//| 2  | 200    |
//| 3  | 300    |
//+----+--------+
// 
//
// 例如上述 Employee 表，SQL查询应该返回 200 作为第二高的薪水。如果不存在第二高的薪水，那么查询应返回 null。 
//
// +---------------------+
//| SecondHighestSalary |
//+---------------------+
//| 200                 |
//+---------------------+
// 
// 👍 629 👎 0


//There is no code of Java type for this problem
select ifnull((select DISTINCT Salary as SecondHighestSalary
        from Employee
        order by Salary desc
        limit 1,1),null) as SecondHighestSalary;

select max(Salary) as SecondHighestSalary
        from Employee
        where Salary < (select max(Salary) from Employee);