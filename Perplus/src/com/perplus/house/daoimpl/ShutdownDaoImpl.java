package com.perplus.house.daoimpl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perplus.house.dao.ShutdownDao;
import com.perplus.house.vo.ShutdownVo;

@Repository
public class ShutdownDaoImpl implements ShutdownDao{
	@Autowired
	private SqlSessionTemplate session;
	@Override
	public int deleteShutdownByHouseSerial(int houseSerial) {
		return session.delete("shutdown.deleteShutdownByHouseSerial",houseSerial);
	}
	@Override
	public int insertShutdown(ShutdownVo shutdown) {
		return session.insert("shutdown.insertShutdown", shutdown);
	}
	
}
