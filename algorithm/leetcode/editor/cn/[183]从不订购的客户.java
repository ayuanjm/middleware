//某网站包含两个表，Customers 表和 Orders 表。编写一个 SQL 查询，找出所有从不订购任何东西的客户。 
//
// Customers 表： 
//
// +----+-------+
//| Id | Name  |
//+----+-------+
//| 1  | Joe   |
//| 2  | Henry |
//| 3  | Sam   |
//| 4  | Max   |
//+----+-------+
// 
//
// Orders 表： 
//
// +----+------------+
//| Id | CustomerId |
//+----+------------+
//| 1  | 3          |
//| 2  | 1          |
//+----+------------+
// 
//
// 例如给定上述表格，你的查询应返回： 
//
// +-----------+
//| Customers |
//+-----------+
//| Henry     |
//| Max       |
//+-----------+
// 
// 👍 169 👎 0


//There is no code of Java type for this problem
select Name as 'Customers' from Customers where id not in (select CustomerId from Orders);
select Name as 'Customers' from Customers where not exists (select id from Orders where CustomerId = Customers.id);