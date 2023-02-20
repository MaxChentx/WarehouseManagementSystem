package com.chentx.tables.module03.dao;


import com.chentx.tables.module03.model.Employee;

import java.sql.*;

/**
 * EmployeeDao class
 *
 * @author MaxBrooks chentingxian195467@163.com
 * @version 2023/2/18 18:05
 * @since JDK17
 */

public class EmployeeDao {
    /**
     * 添加职工信息
     * @param connection    数据库连接
     * @param employee  职工对象
     * @return  添加的记录条数
     * @throws SQLException 可能会无法插入
     */
    public int addEmployee(Connection connection, Employee employee) throws SQLException {
        String sql = "INSERT INTO employeeinformationsheet VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, employee.getEmployeeNumber());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getPosition());
        preparedStatement.setByte(4, employee.getInOffice());
        preparedStatement.setByte(5, employee.getSex());
        preparedStatement.setInt(6, employee.getAge());
        preparedStatement.setString(7, employee.getContact());
        preparedStatement.setDouble(8, employee.getSalary());
        preparedStatement.setDate(9, (Date) employee.getDateOfEntry());
        preparedStatement.setDate(10, (Date) employee.getDateOfDeparture());

        return preparedStatement.executeUpdate();
    }


}

//    /**
//     * 查找书籍
//     * @param connection    连接数据库
//     * @param book  书籍
//     * @return  the number of being effected rows
//     * @throws Exception    how do I know
//     */
//    public ResultSet list(Connection connection, Book book)throws Exception{
//        StringBuilder stringBuffer = new StringBuilder("SELECT * FROM t_book b,t_bookType bt WHERE b.bookTypeId = bt.id");
//        if(StringUtil.isNotEmpty(book.getBookName())){
//            stringBuffer.append(" and b.bookName like '%").append(book.getBookName()).append("%'");
//        }
//        if(StringUtil.isNotEmpty(book.getAuthor())){
//            stringBuffer.append(" and b.author like '%").append(book.getAuthor()).append("%'");
//        }
//        if(book.getBookTypeId() != null && book.getBookTypeId()!=-1){
//            stringBuffer.append(" and b.bookTypeId=").append(book.getBookTypeId());
//        }
//        PreparedStatement preparedStatement = connection.prepareStatement(stringBuffer.toString());
//        return preparedStatement.executeQuery();
//    }


//    /**
//     * 删除书籍
//     * @param connection    连接数据库
//     * @param id 书籍id号
//     * @return  返回删除掉的记录数量
//     * @throws Exception    可能会无法删除
//     */
//    public int delete(Connection connection,String id)throws Exception{
//        String sql="DELETE FROM t_book WHERE id = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, id);
//        return preparedStatement.executeUpdate();
//    }

//    /**
//     *  更新书籍
//     * @param connection    连接数据库
//     * @param book  书籍
//     * @return  返回更新的记录数量
//     * @throws Exception    可能无法更新
//     */
//    public int update(Connection connection,Book book) throws Exception{
//        String sql="UPDATE t_book SET bookName = ?, author = ?, sex = ?, price = ?, bookDesc = ?, bookTypeId = ? WHERE id = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, book.getBookName());
//        preparedStatement.setString(2, book.getAuthor());
//        preparedStatement.setString(3, book.getSex());
//        preparedStatement.setFloat(4, book.getPrice());
//        preparedStatement.setString(5, book.getBookDesc());
//        preparedStatement.setInt(6, book.getBookTypeId());
//        preparedStatement.setInt(7, book.getId());
//        return preparedStatement.executeUpdate();
//    }


//    /**
//     * 查看书籍分类下的书籍数
//     * @param connection    连接数据库
//     * @param bookTypeId    书籍分类
//     * @return  是否存在书籍对应分类下的书籍
//     * @throws Exception    how do I know
//     */
//    public boolean existBookByBookTypeId(Connection connection,String bookTypeId)throws Exception{
//        String sql="SELECT * FROM t_book WHERE bookTypeId = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, bookTypeId);
//        ResultSet rs = preparedStatement.executeQuery();
//        return rs.next();
//    }
