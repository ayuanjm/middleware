//编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。 
//
// 示例： 
//
// +----+---------+
//| Id | Email   |
//+----+---------+
//| 1  | a@b.com |
//| 2  | c@d.com |
//| 3  | a@b.com |
//+----+---------+
// 
//
// 根据以上输入，你的查询应返回以下结果： 
//
// +---------+
//| Email   |
//+---------+
//| a@b.com |
//+---------+
// 
//
// 说明：所有电子邮箱都是小写字母。 
// 👍 220 👎 0


//There is no code of Java type for this problem

select Email from Person group by Email having count(Email) > 1