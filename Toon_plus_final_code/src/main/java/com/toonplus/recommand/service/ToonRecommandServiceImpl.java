package com.toonplus.recommand.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import com.toonplus.recommand.dao.ToonRecommandMapper;
import com.toonplus.recommand.dto.ToonRecommand;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ToonRecommandServiceImpl {

	private final ToonRecommandMapper toonRecommandMapper;
	@Autowired
	public ToonRecommandServiceImpl(ToonRecommandMapper toonRecommandMapper) {
		this.toonRecommandMapper = toonRecommandMapper;
	}

	public String getTopCount(String userId)
	{

		ToonRecommand toonRecommand = toonRecommandMapper.selectToonRecommandByUserId(userId);

		int max = 0;
		String result = "";
		if(toonRecommand.getGenre_Action() > max)
		{
			max = toonRecommand.getGenre_Action();
			result = "#액션";
		}
		if(toonRecommand.getGenre_Academy() > max)
		{
			max = toonRecommand.getGenre_Academy();
			result = "#학원";
		}
		if(toonRecommand.getGenre_Comedy() > max)
		{
			max = toonRecommand.getGenre_Comedy();
			result = "#코미디/일상";
		}
		if(toonRecommand.getGenre_Drama() > max)
		{
			max = toonRecommand.getGenre_Drama();
			result = "#드라마";
		}
		if(toonRecommand.getGenre_Fantasy() > max)
		{
			max = toonRecommand.getGenre_Fantasy();
			result = "#판타지";
		}
		if(toonRecommand.getGenre_Romance() > max)
		{
			max = toonRecommand.getGenre_Romance();
			result = "#로맨스";
		}
		if(toonRecommand.getGenre_Thriller() > max)
		{
			max = toonRecommand.getGenre_Thriller();
			result = "#스릴러";
		}
		if(max == 0)
		{
			Random random = new Random();
			int randomIndex = random.nextInt(7);
			if(randomIndex == 0)
			{
				result = "#액션";
			}
			else if(randomIndex == 1)
			{
				result = "#학원";
			}
			else if(randomIndex == 2)
			{
				result = "#코미디/일상";
			}
			else if(randomIndex == 3)
			{
				result = "#드라마";
			}
			else if(randomIndex == 4)
			{
				result = "#판타지";
			}
			else if(randomIndex == 5)
			{
				result = "#로맨스";
			}
			else
			{
				result = "#스릴러";
			}

		}

		return result;
	}
	public void countUpdate(String genre, String userId)
	{
		toonRecommandMapper.updateToonCount(genre, userId);
	}

}
