package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;

@Mapper
public interface FileMapper {
	@Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)"
			+ "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{filedata})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insert(FileModel file);

	@Select("SELECT * FROM FILES WHERE filename = #{filename}")
	FileModel getFile(String filename);

	@Select("Select * from FILES where fileId= #{fileId}")
	FileModel viewFile(Integer fileId);

	@Select("Select * from FILES where userId= #{userId}")
	List<FileModel> viewFiles(Integer userId);

	@Delete("Delete from FILES where fileId= #{fileId}")
	int delete(Integer fileId);
}
