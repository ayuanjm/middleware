//编写一个 SQL 查询，来删除 Person 表中所有重复的电子邮箱，重复的邮箱里只保留 Id 最小 的那个。 
//
// +----+------------------+
//| Id | Email            |
//+----+------------------+
//| 1  | john@example.com |
//| 2  | bob@example.com  |
//| 3  | john@example.com |
//+----+------------------+
//Id 是这个表的主键。
// 
//
// 例如，在运行你的查询语句之后，上面的 Person 表应返回以下几行: 
//
// +----+------------------+
//| Id | Email            |
//+----+------------------+
//| 1  | john@example.com |
//| 2  | bob@example.com  |
//+----+------------------+
// 
//
// 
//
// 提示： 
//
// 
// 执行 SQL 之后，输出是整个 Person 表。 
// 使用 delete 语句。 
// 
// 👍 271 👎 0


//There is no code of Java type for this problem
delete p1 from
        Person p1,
        (select min(Id)as Id,Email from Person group by Email Having count(1)>1)p2
        where p1.Id=p2.Id and p1.Email = p2.Email;

DELETE p1 FROM Person p1,
        Person p2
        WHERE
        p1.Email = p2.Email AND p1.Id > p2.Id;