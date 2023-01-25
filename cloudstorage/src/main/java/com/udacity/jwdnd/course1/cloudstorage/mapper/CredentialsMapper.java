package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface CredentialsMapper {
	@Insert("INSERT INTO CREDENTIALS (username,key, password, url, userid)"
			+ "VALUES(#{username},#{key}, #{password}, #{url}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialid")
	boolean insert(Credentials credentials);

	@Select("Select * from CREDENTIALS where credentialid= #{credentialid}")
	Credentials viewCredentials(Integer credentialid);

	@Select("Select * from CREDENTIALS where userid= #{userId}")
	List<Credentials> viewAllCredentials(Integer userId);

	@Delete("Delete from CREDENTIALS where credentialid= #{credentialid}")
	int delete(Integer credentialid);

	@Update("Update CREDENTIALS set username=#{username}, "
			+ "key=#{key}, password =#{password}, url=#{url}, userid=#{userId} where credentialid=#{credentialid}")
	boolean update(Credentials credentials);
}
