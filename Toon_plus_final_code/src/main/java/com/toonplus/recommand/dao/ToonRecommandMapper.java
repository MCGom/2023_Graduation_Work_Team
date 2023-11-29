package com.toonplus.recommand.dao;

import com.toonplus.recommand.dto.ToonRecommand;
import org.apache.ibatis.annotations.Param;

//20230920추가
public interface ToonRecommandMapper {
		ToonRecommand selectToonRecommandByUserId(@Param("USER_ID") String userId);
		void updateToonCount(@Param("Genre") String genre, @Param("USER_ID") String userId);
}