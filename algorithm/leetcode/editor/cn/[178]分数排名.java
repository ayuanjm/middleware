//编写一个 SQL 查询来实现分数排名。 
//
// 如果两个分数相同，则两个分数排名（Rank）相同。请注意，平分后的下一个名次应该是下一个连续的整数值。换句话说，名次之间不应该有“间隔”。 
//
// +----+-------+
//| Id | Score |
//+----+-------+
//| 1  | 3.50  |
//| 2  | 3.65  |
//| 3  | 4.00  |
//| 4  | 3.85  |
//| 5  | 4.00  |
//| 6  | 3.65  |
//+----+-------+
// 
//
// 例如，根据上述给定的 Scores 表，你的查询应该返回（按分数从高到低排列）： 
//
// +-------+------+
//| Score | Rank |
//+-------+------+
//| 4.00  | 1    |
//| 4.00  | 1    |
//| 3.85  | 2    |
//| 3.65  | 3    |
//| 3.65  | 3    |
//| 3.50  | 4    |
//+-------+------+
// 
//
// 重要提示：对于 MySQL 解决方案，如果要转义用作列名的保留字，可以在关键字之前和之后使用撇号。例如 `Rank` 
// 👍 588 👎 0


//There is no code of Java type for this problem
select a.Score,
        (select count(distinct b.Score)
        from Scores b
        where b.Score >= a.Score) as 'Rank'
        from Scores a
        order by a.Score desc;

select Score,
dense_rank() over(order by Score desc) as 'Rank'
from Scores;