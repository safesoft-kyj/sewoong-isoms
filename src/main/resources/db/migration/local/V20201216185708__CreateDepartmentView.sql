CREATE OR ALTER view [dbo].[vw_department_tree]
as
WITH tree_query AS (
SELECT
id,
pid,
name,
convert(varchar(255), id) sort,
convert(varchar(255), name) depth_fullname
FROM department WHERE pid is null
UNION ALL
SELECT
B.id,
B.pid, 
B.name,
convert(varchar(255), convert(nvarchar,C.sort) + ' > ' + convert(varchar(255), B.id)) sort,
convert(varchar(255), convert(nvarchar,C.depth_fullname) + ' > ' + convert(varchar(255), B.name)) depth_fullname
FROM
department B, tree_query C
WHERE B.pid = C.id )
SELECT TOP 1000
id,
pid,
name,
depth_fullname
FROM tree_query
order by sort;