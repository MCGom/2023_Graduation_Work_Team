package com.toonplus.toon.service;

import com.toonplus.toon.dao.ToonMapper;
import com.toonplus.toon.dto.Toon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
//20230920추가

@Service
public class ToonServiceImpl {

	private final ToonMapper toonMapper;



	@Autowired
	public ToonServiceImpl(ToonMapper toonMapper) {
		this.toonMapper = toonMapper;
	}
	// mysql 연동 20230920추가
	// "" 큰따옴표 제거 로직


	public List<Toon> getAllToons() {
		List<Toon> toons = toonMapper.selectToon();
		for (Toon toon : toons) {
			toon.setTOON_IMAGELINK(toon.getTOON_IMAGELINK().replace("\"", ""));
			toon.setTOON_LINK(toon.getTOON_LINK().replace("\"", ""));
			toon.setTOON_PLATFORM(toon.getTOON_PLATFORM());
			toon.setTOON_NAME(toon.getTOON_NAME());
			toon.setTOON_LIKES(toon.getTOON_LIKES());
			toon.setToon_category((toon.getToon_category()));
		}
		return toons;
	}



	public Toon getToonByTitle(String webtoonTitle) {
		return toonMapper.selectToonByTitle(webtoonTitle);
	}

	public List<Toon> allReview(String userId){
		List<Toon> toonR = toonMapper.toonReview(userId);
		for (Toon toonRv : toonR) {
			String[] userIdArray = toonRv.getUser_id().split(";");
			String[] reviewContentArray = toonRv.getReview_content().split(";");


			List<String> userIdList = Arrays.asList(userIdArray);
			List<String> reviewContentList = Arrays.asList(reviewContentArray);


			int userIndex = userIdList.indexOf(userId);


			if (userIndex != -1 && userIndex < reviewContentList.size()) {
				toonRv.setReview_content(reviewContentList.get(userIndex));
			}


			toonRv.setUser_id(null);
		}
		return toonR;
	}

	
	public List<Toon> getRandomallToons(List<Toon> toons, int numToselect) {
        int totalToons = toons.size();
        
        List<Toon> selectedToons = new ArrayList<>();
        Random random = new Random();


        for (int i = 0; i < numToselect; i++) {
            int randomIndex = random.nextInt(totalToons);
            selectedToons.add(toons.get(randomIndex));
        }

        return selectedToons;
    }

	// 20231013 추가 - 랜덤 전체 조회
	public List<Toon> searchToons() {
		List<Toon> toons = toonMapper.selectToon();
		return toons;
	}
	
	public List<Toon> newToon1() {
		List<Toon> newt1 = toonMapper.newToon1();
		return newt1;
	}

	public List<Toon> newToon2() {
		List<Toon> newt2 = toonMapper.newToon2();
		return newt2;
	}

	public List<Toon> newToon3() {
		List<Toon> newt3 = toonMapper.newToon3();
		return newt3;
	}

	public List<Toon> upToon1() {
		List<Toon> upt1 = toonMapper.upToon1();



		return upt1;
	}

	public List<Toon> upToon2() {
		List<Toon> upt2 = toonMapper.upToon2();



		return upt2;
	}

	public List<Toon> upToon3() {
		List<Toon> upt3 = toonMapper.upToon3();


		return upt3;
	}
	public List<Toon> getRandomUpToons1(List<Toon> upt1, int numToselect) {
		int totalToons = upt1.size();

		List<Toon> selectedToons = new ArrayList<>();
		Random random = new Random();

		// 랜덤하게 3개의 요소 선택
		for (int i = 0; i < numToselect; i++) {
			int randomIndex = random.nextInt(totalToons);
			selectedToons.add(upt1.get(randomIndex));
		}

		return selectedToons;
	}

	public List<Toon> getRandomUpToons2(List<Toon> upt2, int numToselect) {
		int totalToons = upt2.size();

		List<Toon> selectedToons = new ArrayList<>();
		Random random = new Random();

		// 랜덤하게 3개의 요소 선택
		for (int i = 0; i < numToselect; i++) {
			int randomIndex = random.nextInt(totalToons);
			selectedToons.add(upt2.get(randomIndex));
		}

		return selectedToons;
	}

	public List<Toon> getRandomUpToons3(List<Toon> upt3, int numToselect) {
		int totalToons = upt3.size();

		List<Toon> selectedToons = new ArrayList<>();
		Random random = new Random();

		// 랜덤하게 3개의 요소 선택
		for (int i = 0; i < numToselect; i++) {
			int randomIndex = random.nextInt(totalToons);
			selectedToons.add(upt3.get(randomIndex));
		}

		return selectedToons;
	}
	public List<Toon> hotToon1() {
		List<Toon> hot1 = toonMapper.hotToon1();
		
		hot1.sort(Comparator.comparing(Toon::getTOON_LIKES).reversed());
		
		return hot1.subList(0,Math.min(hot1.size(), 3));
	}
	public List<Toon> hotToon2() {
		List<Toon> hot2 = toonMapper.hotToon2();
		
		hot2.sort(Comparator.comparing(Toon::getTOON_LIKES).reversed());
		
		return hot2.subList(0,Math.min(hot2.size(), 3));
	}
	public List<Toon> hotToon3() {
		List<Toon> hot3 = toonMapper.hotToon3();
		
		hot3.sort(Comparator.comparing(Toon::getTOON_LIKES).reversed());
		
		return hot3.subList(0,Math.min(hot3.size(), 3));
	}




	public List<Toon> getRandomToons1(List<Toon> newt1, int numToselect) {
        int totalToons = newt1.size();
        
        List<Toon> selectedToons = new ArrayList<>();
        Random random = new Random();

        // 랜덤하게 3개의 요소 선택
        for (int i = 0; i < numToselect; i++) {
            int randomIndex = random.nextInt(totalToons);
            selectedToons.add(newt1.get(randomIndex));
        }

        return selectedToons;
    }
	
	

    public List<Toon> getRandomToons2(List<Toon> newt2, int numToselect) {
        int totalToons = newt2.size();
        List<Toon> selectedToons = new ArrayList<>();
        Random random = new Random();

        // 랜덤하게 3개의 요소 선택
        for (int i = 0; i < numToselect; i++) {
            int randomIndex = random.nextInt(totalToons);
            selectedToons.add(newt2.get(randomIndex));
        }
        return selectedToons;
    }


    public List<Toon> getRandomToons3(List<Toon> newt3, int numToselect) {
        int totalToons = newt3.size();
        
        List<Toon> selectedToons = new ArrayList<>();
        Random random = new Random();

        // 랜덤하게 3개의 요소 선택
        for (int i = 0; i < numToselect; i++) {
            int randomIndex = random.nextInt(totalToons);
            selectedToons.add(newt3.get(randomIndex));
        }

        return selectedToons;
    }
	// 20231013 추가 - 이름순 전체 조회
	public List<Toon> searchToonsByName() {
		List<Toon> toons = toonMapper.selectToonByName();
		return toons;
	}

	public List<Toon> searchToonByLikes() {
		List<Toon> toons = toonMapper.selectToonByLikes();
		return toons;
	}

	public List<Toon> searchToonsByKeyword(String keyword) {
		List<Toon> toons = toonMapper.selectKeywordToon(keyword);
		return toons;
	}

	// 20231013 추가 - 태그에 맞는 랜덤 조회
	public List<Toon> tagToonsByKeyword(String keyword) {
		List<Toon> toons = toonMapper.tagKeywordToon(keyword);
		return toons;
	}


	// 20231013 추가 - 태그에 맞는 이름순 조회
	public List<Toon> tagToonsByKeywordByName(String keyword) {
		List<Toon> toons = toonMapper.tagKeywordToonByName(keyword);
		return toons;
	}

	// 태그에 맞는 좋아요순 조회
	public List<Toon> tagToonsByKeywordByLikes(String keyword) {
		List<Toon> toons = toonMapper.tagKeywordToonByLikes(keyword);
		return toons;
	}

	// 좋아요순으로 전체 조회
	public List<Toon> selectToonByLikes() {
		List<Toon> toons = toonMapper.selectToonByLikes();
		for (Toon toon : toons) {
			// 이미지 링크 등의 처리는 필요에 따라 추가하세요
		}
		return toons;
	}

}
