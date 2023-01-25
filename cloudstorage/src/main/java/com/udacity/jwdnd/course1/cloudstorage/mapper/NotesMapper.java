package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
@Mapper
public interface NotesMapper {
	@Insert("INSERT INTO NOTES (title, description, userid)" +
            "VALUES(#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);
	
	 @Update("Update NOTES set title=#{title}, " +
		        " description=#{description} where noteid=#{noteid}")
    int update(Note note);
	
	@Select("Select * from NOTES where noteid= #{noteid}")
	Note viewNotes(Integer noteid);
	
	@Select("Select * from NOTES where userid= #{userId}")
	List<Note> viewAllNotes(Integer userId);
	
	@Delete("Delete from NOTES where noteid= #{noteid}")
	int delete(Integer noteid);
	

	 @Select("SELECT * FROM NOTES WHERE notetitle = #{title}")
	 Note getNote(String title);

}
