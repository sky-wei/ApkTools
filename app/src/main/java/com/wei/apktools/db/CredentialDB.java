package com.wei.apktools.db;

import com.wei.apktools.core.SignedProperty;
import com.wei.apktools.utils.Log;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialDB extends SqliteDB {

	public static final String DB_NAME = "credential.db";
	
	public CredentialDB() {
		super(DB_NAME);
	}
	
	@Override
	public void create(Statement statement) {
		
		try {
			String sql = "create table credential("
					+ "id integer primary key autoincrement,"
					+ "keystorePassword varchar(100),"
					+ "term integer,"
					+ "alias varchar(100),"
					+ "password varchar(100),"
					+ "name varchar(100),"
					+ "organization varchar(50),"
					+ "city varchar(50),"
					+ "province varchar(50),"
					+ "code varchar(20),"
					+ "filePath varchar(100),"
					+ "createTime varchar(50)"
					+ ");";
			
			statement.execute(sql);
		} catch (SQLException e) {
			Log.e("执行SQL语句失败!", e);
		}
	}
	
	public List<SignedProperty> queryCredentials() {
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select id, keystorePassword, term, alias, password, name, organization, city, province, code, filePath, createTime from credential;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			
			List<SignedProperty> signerInfos = new ArrayList<>();
			
			while (resultSet.next()) {

				SignedProperty signerInfo = new SignedProperty();
				
				signerInfo.setId(resultSet.getInt("id"));
				signerInfo.setKeystorePassword(resultSet.getString("keystorePassword"));
				signerInfo.setTerm(resultSet.getInt("term"));
				signerInfo.setSignedName(resultSet.getString("alias"));
				signerInfo.setSignedPassword(resultSet.getString("password"));
				signerInfo.setName(resultSet.getString("name"));
				signerInfo.setOrganization(resultSet.getString("organization"));
				signerInfo.setCity(resultSet.getString("city"));
				signerInfo.setProvince(resultSet.getString("province"));
				signerInfo.setCode(resultSet.getString("code"));
				signerInfo.setFile(new File(resultSet.getString("filePath")));
				signerInfo.setCreateTime(resultSet.getString("createTime"));
				
				signerInfos.add(signerInfo);
			}
			
			return signerInfos;
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e);
		} catch (SQLException e) {
			Log.e("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return null;
	}
	
	public SignedProperty queryCredential(int id) {
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select id, keystorePassword, term, alias, password, name, organization, city, province, code, filePath, createTime from credential where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				SignedProperty signerInfo = new SignedProperty();
				
				signerInfo.setId(resultSet.getInt("id"));
				signerInfo.setKeystorePassword(resultSet.getString("keystorePassword"));
				signerInfo.setTerm(resultSet.getInt("term"));
				signerInfo.setSignedName(resultSet.getString("alias"));
				signerInfo.setSignedPassword(resultSet.getString("password"));
				signerInfo.setName(resultSet.getString("name"));
				signerInfo.setOrganization(resultSet.getString("organization"));
				signerInfo.setCity(resultSet.getString("city"));
				signerInfo.setProvince(resultSet.getString("province"));
				signerInfo.setCode(resultSet.getString("code"));
				signerInfo.setFile(new File(resultSet.getString("filePath")));
				signerInfo.setCreateTime(resultSet.getString("createTime"));
				
				return signerInfo;
			}
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e);
		} catch (SQLException e) {
			Log.e("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return null;
	}
	
	public boolean insertCredential(SignedProperty signerInfo) {
		
		if (signerInfo == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "insert into credential(keystorePassword, term, alias, password, name, organization, city, province, code, filePath, createTime)"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, signerInfo.getKeystorePassword());
			preparedStatement.setInt(2, signerInfo.getTerm());
			preparedStatement.setString(3, signerInfo.getSignedName());
			preparedStatement.setString(4, signerInfo.getSignedPassword());
			preparedStatement.setString(5, signerInfo.getName());
			preparedStatement.setString(6, signerInfo.getOrganization());
			preparedStatement.setString(7, signerInfo.getCity());
			preparedStatement.setString(8, signerInfo.getProvince());
			preparedStatement.setString(9, signerInfo.getCode());
			preparedStatement.setString(10, signerInfo.getFile().getPath());
			preparedStatement.setString(11, signerInfo.getCreateTime());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e);
		} catch (SQLException e) {
			Log.e("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	public boolean deleteCredential(int id) {
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "delete from credential where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e);
		} catch (SQLException e) {
			Log.e("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	public boolean updateCredential(SignedProperty signerInfo) {
		
		if (signerInfo == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "update credential set keystorePassword = ?, term = ?, alias = ?, password = ?, name = ?, organization = ?,"
					+ "city = ?, province = ?, code = ?, filePath = ?, createTime = ? where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, signerInfo.getKeystorePassword());
			preparedStatement.setInt(2, signerInfo.getTerm());
			preparedStatement.setString(3, signerInfo.getSignedName());
			preparedStatement.setString(4, signerInfo.getSignedPassword());
			preparedStatement.setString(5, signerInfo.getName());
			preparedStatement.setString(6, signerInfo.getOrganization());
			preparedStatement.setString(7, signerInfo.getCity());
			preparedStatement.setString(8, signerInfo.getProvince());
			preparedStatement.setString(9, signerInfo.getCode());
			preparedStatement.setString(10, signerInfo.getFile().getPath());
			preparedStatement.setString(11, signerInfo.getCreateTime());
			preparedStatement.setInt(12, signerInfo.getId());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e);
		} catch (SQLException e) {
			Log.e("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
}
