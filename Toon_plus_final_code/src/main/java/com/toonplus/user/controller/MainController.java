package com.toonplus.user.controller;

import com.toonplus.email.dto.EmailVO;
import com.toonplus.email.service.EmailServiceImpl;
import com.toonplus.recommand.service.ToonRecommandServiceImpl;
import com.toonplus.review.dto.ReviewObject;
import com.toonplus.review.dto.ToonReview;
import com.toonplus.review.service.ToonReviewServiceImpl;
import com.toonplus.toon.dto.Toon;
import com.toonplus.toon.service.ToonServiceImpl;
import com.toonplus.user.dto.User;
import com.toonplus.user.service.TextStorageService;
import com.toonplus.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ToonServiceImpl toonService;


    @Autowired
    private TextStorageService textStorageService;

    @Autowired
    private ToonReviewServiceImpl toonReviewService;


    @Autowired
    private ToonRecommandServiceImpl toonRecommandService;

    @PostMapping("/user/checkId")  //아이디 체크
    @ResponseBody
    public String checkId(User user) {
        String RESULT;

        int cnt = userService.checkId(user);
        if (cnt > 0) {
            RESULT = "false";
        } else {
            RESULT = "true";
        }
        System.out.println("RESULT : " + RESULT);
        return RESULT;
    }

    @PostMapping("/user/checkEmail")    //이메일 체크
    @ResponseBody
    public String checkEmail(User user, HttpSession session) {
        String RESULT = "";
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser != null) {
            user.setUSER_ID(sessionUser.getUSER_ID());
        }
        int cnt = userService.checkEmail(user);
        if (cnt > 0) {
            RESULT = "false";
        } else {
            RESULT = "true";
        }
        System.out.println("RESULT : " + RESULT);
        return RESULT;
    }

    @PostMapping("/user/sendEmail")    //이메일 체크
    @ResponseBody
    public String sendEmail(User user, HttpSession session) {
        String RESULT = "";
        try {
            String authNum = numberGen(6, 1);
            EmailVO vo = new EmailVO(user.getUSER_MAIL(), "[toonplus] 인증메일입니다.", "[" + authNum + "]" + "입니다.");
            emailService.sendSimpleMessage(vo);
            RESULT = authNum;
        } catch (Exception e) {

            RESULT = "";
        }

        System.out.println("RESULT : " + RESULT);
        return RESULT;
    }

    @PostMapping("/user/checkNickName")    //닉네임 체크
    @ResponseBody
    public String checkNickName(User user, HttpSession session) {
        String RESULT;
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser != null) {
            user.setUSER_ID(sessionUser.getUSER_ID());
        }
        int cnt = userService.checkNickName(user);
        if (cnt > 0) {
            RESULT = "false";
        } else {
            RESULT = "true";
        }
        System.out.println("RESULT : " + RESULT);
        return RESULT;
    }

    @PostMapping("/user/signup")    //회원가입하면 홈으로 다시 전송
    public String signupPost(User user) {
        System.out.println(user.toString());
        userService.insertUser(user);
        userService.insertcount(user);
        return "redirect:/home";
    }

    @GetMapping("/user/signup")  //회원가입 화면 띄우기
    public String signup() {

        return "/user/signupForm";
    }


    @PostMapping("/user/update")  //회원정보 수정하면 메인으로 전송
    public String updatePost(User user, HttpSession session) {
        System.out.println(user.toString());
        userService.updateUser(user);
        User rs = userService.login(user);
        session.setAttribute("user", rs);
        return "redirect:/user/loginForm";
    }

    @GetMapping("/user/update")  //회원정보 수정 화면 띄우기
    public String update(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if(user != null && !"0000".equals(user.getUSER_ID()))
        {
            model.addAttribute("user", user);
        }
        return "/user/updateForm";
    }

    @GetMapping("/user/mypage")
    public String mypage(HttpSession session, Model model) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            User guest = new User();
            guest.setUSER_ID("0000");
            guest.setUSER_PASSWORD("0000");
            guest.setUSER_FAVORITE("");
            guest.setUSER_LIKE("");
            guest.setUSER_NICKNAME("0000");
            guest.setUSER_MAIL("0000");
            guest.setUSER_NAME("0000");
            session.setAttribute("user", guest);
            return "redirect:main";
        }if (user != null && !"0000".equals(user.getUSER_ID())) {
            String userId = user.getUSER_ID();


            // 1. USER_FAVORITE 필드에서 웹툰 제목을 가져와 파싱
            String favoriteWebtoons = user.getUSER_FAVORITE();

            if (favoriteWebtoons != null && !favoriteWebtoons.isEmpty()) {
                String[] favoriteWebtoonArray = favoriteWebtoons.split(",");

                List<Toon> favoriteToons = new ArrayList<>();

                // 2. 각 웹툰 제목을 사용하여 해당 웹툰 정보를 가져옴
                for (String webtoonTitle : favoriteWebtoonArray) {
                    Toon toon = toonService.getToonByTitle(webtoonTitle);
                    if (toon != null) {
                        favoriteToons.add(toon);
                    }
                }

                // favoriteToons를 모델에 추가
                model.addAttribute("favoriteToons", favoriteToons);
            } else {
                model.addAttribute("noFavoriteToonsMessage", "즐겨찾기한 웹툰이 없습니다.");

            }


            List<Toon> allToons = toonService.getAllToons();


            List<Toon> randomallToonReview = toonService.allReview(userId);


            List<Toon> randomallToons = toonService.getRandomallToons(allToons, 9);

            logger.info("allToons" + allToons);
            logger.info("randomallToonReview" + randomallToonReview);
            model.addAttribute("toonR", randomallToonReview);
            model.addAttribute("toons", randomallToons);

            model.addAttribute("currentUser", user);

            // 메모장에서 저장한 메모를 불러옴
            String memoText = textStorageService.loadText(userId);
            model.addAttribute("memoOwnerId", userId);
            model.addAttribute("memoText", memoText);

            return "user/mypage"; // 마이페이지 화면으로 이동
        } else {
            return "redirect:main";
        }
    }

    @PostMapping("/user/mypage")
    public String saveMemo(HttpSession session, @RequestParam String memoText) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUSER_ID();


            // 메모장에 메모 저장
            textStorageService.saveText(userId, memoText);

            return "redirect:/user/mypage";
        } else {
            return "redirect:/user/loginForm";
        }
    }

    @GetMapping("/opinion")
    public String opinion(@RequestParam(name="reviewButton", required = true) String toonName, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            User guest = new User();
            guest.setUSER_ID("0000");
            guest.setUSER_PASSWORD("0000");
            guest.setUSER_FAVORITE("");
            guest.setUSER_LIKE("");
            guest.setUSER_NICKNAME("0000");
            guest.setUSER_MAIL("0000");
            guest.setUSER_NAME("0000");
            session.setAttribute("user", guest);
            return "redirect:main";
        } if (user != null && !"0000".equals(user.getUSER_ID())) {
            Toon toon = toonService.getToonByTitle(toonName);
            ToonReview toonReview = toonReviewService.getToonReviewByName(toonName);
            List<ReviewObject> reviewObjects = new ArrayList<>();
            ReviewObject writerObject =new ReviewObject();
            writerObject.setUSER_ID(user.getUSER_ID());
            String loadedReviews = toonReview.getREVIEW_CONTENT();
            String loadedUsers = toonReview.getUSER_ID();
            String[] reviewArray = null;
            String[] userArray = null;
            if (loadedReviews != null && !loadedReviews.isEmpty()) {
                reviewArray = loadedReviews.split(";");

            }
            if (loadedUsers != null && !loadedUsers.isEmpty()) {
                userArray = loadedUsers.split(";");
            }
            if (userArray != null && reviewArray != null) {
                int i = 0;
                for (String reviews : reviewArray) {
                    ReviewObject reviewObject = new ReviewObject();
                    reviewObject.setREVIEW_CONTENT(reviews);
                    reviewObject.setUSER_ID(userArray[i]);
                    User tmpUser = userService.getUser(userArray[i]);
                    reviewObject.setUSER_NICKNAME(tmpUser.getUSER_NICKNAME());
                    reviewObjects.add(reviewObject);
                    if(userArray[i].equals(user.getUSER_ID()))
                    {
                        writerObject.setREVIEW_CONTENT(reviews);
                    }
                    i++;
                }
            }
            model.addAttribute("reviews", reviewObjects);
            model.addAttribute("toonImage", toon);

            model.addAttribute("writer", writerObject);
            return "/opinion";
        } else {
            // 로그인되지 않은 사용자의 경우 로그인 페이지로 이동
            return "redirect:main";
        }
    }

    @PostMapping("reviewSubmit")
    public String reviewSubmited(ReviewObject reviewObject, Toon toon, @RequestParam(name="NEW_REVIEW_CONTENT") String new_reviewContent, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            User guest = new User();
            guest.setUSER_ID("0000");
            guest.setUSER_PASSWORD("0000");
            guest.setUSER_FAVORITE("");
            guest.setUSER_LIKE("");
            guest.setUSER_NICKNAME("0000");
            guest.setUSER_MAIL("0000");
            guest.setUSER_NAME("0000");
            session.setAttribute("user", guest);
            return "redirect:main";
        } if (user != null && !"0000".equals(user.getUSER_ID())) {
            Toon targetToon = toonService.getToonByTitle(toon.getTOON_NAME());
            ToonReview toonReview = toonReviewService.getToonReviewByName(targetToon.getTOON_NAME());
            String new_toonReviewContent = "";
            String new_toonReviewId = "";
            if (new_reviewContent == null || new_reviewContent.isEmpty()) {
                new_toonReviewId = reviewObject.getUSER_ID() + ";";
                new_toonReviewContent = " ;";
            } else if (toonReview.getUSER_ID() == null) {
                new_toonReviewId = reviewObject.getUSER_ID() + ";";
                new_toonReviewContent = new_reviewContent + ";";
            } else if (toonReview.getUSER_ID().contains(reviewObject.getUSER_ID())) {
                new_toonReviewContent = toonReview.getREVIEW_CONTENT();
                new_toonReviewContent = new_toonReviewContent.replace(reviewObject.getREVIEW_CONTENT(), new_reviewContent);
                new_toonReviewId = toonReview.getUSER_ID();
            } else {
                new_toonReviewId = toonReview.getUSER_ID() + reviewObject.getUSER_ID() + ";";
                new_toonReviewContent = toonReview.getREVIEW_CONTENT() + new_reviewContent + ";";
            }
            toonReviewService.updateToonReview(new_toonReviewContent, targetToon.getTOON_NAME(), new_toonReviewId);
            targetToon = toonService.getToonByTitle(toon.getTOON_NAME());
            toonReview = toonReviewService.getToonReviewByName(targetToon.getTOON_NAME());
            List<ReviewObject> reviewObjects = new ArrayList<>();
            ReviewObject writerObject = new ReviewObject();
            writerObject.setUSER_ID(reviewObject.getUSER_ID());
            String loadedReviews = toonReview.getREVIEW_CONTENT();
            String loadedUsers = toonReview.getUSER_ID();
            String[] reviewArray = null;
            String[] userArray = null;
            if (loadedReviews != null && !loadedReviews.isEmpty()) {
                reviewArray = loadedReviews.split(";");

            }
            if (loadedUsers != null && !loadedUsers.isEmpty()) {
                userArray = loadedUsers.split(";");
            }
            if (userArray != null && reviewArray != null) {
                int i = 0;
                for (String reviews : reviewArray) {
                    ReviewObject Object = new ReviewObject();
                    Object.setREVIEW_CONTENT(reviews);
                    Object.setUSER_ID(userArray[i]);
                    User tmpUser = userService.getUser(userArray[i]);
                    Object.setUSER_NICKNAME(tmpUser.getUSER_NICKNAME());
                    reviewObjects.add(Object);
                    if (userArray[i].equals(reviewObject.getUSER_ID())) {
                        writerObject.setREVIEW_CONTENT(reviews);
                    }
                    i++;
                }
            }
            model.addAttribute("reviews", reviewObjects);
            model.addAttribute("toonImage", targetToon);

            model.addAttribute("writer", writerObject);
            return "opinion";
        }
        else
        {
            return "redirect:main";
        }
    }


    @GetMapping("reviewToMypage")
    public String reviewToMypageFunc(@RequestParam(name="user_id") String userId, Model model, HttpSession session, Toon toonObject)throws IOException {
        User now_user = (User) session.getAttribute("user");
        if (!now_user.getUSER_ID().equals(userId)) {
            User user = userService.getUser(userId);
            if(user.getUSER_MYPAGE().equals("Y"))
            {
                String user_id = user.getUSER_ID();
                System.out.println(user_id);
                // 1. USER_FAVORITE 필드에서 웹툰 제목을 가져와 파싱
                String favoriteWebtoons = user.getUSER_FAVORITE();

                if (favoriteWebtoons != null && !favoriteWebtoons.isEmpty()) {
                    String[] favoriteWebtoonArray = favoriteWebtoons.split(",");

                    List<Toon> favoriteToons = new ArrayList<>();

                    // 2. 각 웹툰 제목을 사용하여 해당 웹툰 정보를 가져옴
                    for (String webtoonTitle : favoriteWebtoonArray) {
                        Toon toon = toonService.getToonByTitle(webtoonTitle);
                        if (toon != null) {
                            favoriteToons.add(toon);
                        }
                    }

                    // favoriteToons를 모델에 추가
                    model.addAttribute("favoriteToons", favoriteToons);

                } else {
                    model.addAttribute("noFavoriteToonsMessage", "즐겨찾기한 웹툰이 없습니다.");

                }

                List<Toon> allToons = toonService.getAllToons();

                List<Toon> randomallToonReview = toonService.allReview(userId);
                List<Toon> randomallToons = toonService.getRandomallToons(allToons, 9);
                logger.info("allToons" + allToons);
                logger.info("randomallToonReview" + randomallToonReview);
                model.addAttribute("toonR", randomallToonReview);
                model.addAttribute("toons", randomallToons);

                model.addAttribute("currentUser", user);

                // 메모장에서 저장한 메모를 불러옴
                String memoText = textStorageService.loadText(user_id);
                model.addAttribute("memoText", memoText);

                return "user/mypage";
            }
            else {
                return opinion(toonObject.getTOON_NAME(), model, session);
            }
        }
        else
        {
            return "redirect:user/mypage";
        }

    }

    @PostMapping("/user/login")
    @ResponseBody
    public String loginPost(User user, HttpSession session) {
        String RESULT;
        User rs = userService.login(user);
        if (rs != null) {
            session.setAttribute("user", rs);
            RESULT = "true";
        } else {
            RESULT = "false";
        }
        System.out.println("RESULT : " + RESULT);
        return RESULT;
    }


    @GetMapping("/user/login")  //로그인 화면 띄우기
    public String login() {

        return "/user/loginForm";
    }

    @GetMapping("/main")
    public String main(HttpSession session , Model model, @RequestParam(value="unlog", required = false) String log) {
        System.out.println(log);
        if(log == null)
        {
            log = " ";
        }
        User user = (User) session.getAttribute("user");
        if (user == null ||  log.equals("unloged")) {
            User guest = new User();
            guest.setUSER_ID("0000");
            guest.setUSER_PASSWORD("0000");
            guest.setUSER_FAVORITE("");
            guest.setUSER_LIKE("");
            guest.setUSER_NICKNAME("0000");
            guest.setUSER_MAIL("0000");
            guest.setUSER_NAME("0000");
            session.setAttribute("user", guest);
            return "redirect:main";
        }
        if (user != null && !"0000".equals(user.getUSER_ID())) {
            // 로그인된 사용자의 경우 메인으로 이동


            String recommandTag = toonRecommandService.getTopCount(user.getUSER_ID());
            List<Toon> allToons = toonService.tagToonsByKeyword(recommandTag);
            List<Toon> randomallToons = toonService.getRandomallToons(allToons, 9);
            List<Toon> newtoon1 = toonService.newToon1();
            List<Toon> randomToons1 = toonService.getRandomToons1(newtoon1, 3);
            List<Toon> newtoon2 = toonService.newToon2();
            List<Toon> randomToons2 = toonService.getRandomToons2(newtoon2, 3);
            List<Toon> newtoon3 = toonService.newToon3();
            List<Toon> randomToons3 = toonService.getRandomToons3(newtoon3, 3);
            List<Toon> hott1 = toonService.hotToon1();
            List<Toon> hott2 = toonService.hotToon2();
            List<Toon> hott3 = toonService.hotToon3();
            List<Toon> up1 = toonService.upToon1();
            List<Toon> randomUpToons1 = toonService.getRandomUpToons1(up1, 3);
            List<Toon> up2 = toonService.upToon2();
            List<Toon> randomUpToons2 = toonService.getRandomUpToons2(up2, 3);
            List<Toon> up3 = toonService.upToon3();
            List<Toon> randomUpToons3 = toonService.getRandomUpToons3(up3, 3);
            logger.info("allToons"+allToons); // 로그 메시지 추가
            logger.info("newToon1"+newtoon1);
            logger.info("newToon2"+newtoon2);
            logger.info("newToon3"+newtoon3);
            model.addAttribute("toons", randomallToons);//20230920추가
            model.addAttribute("newt1", randomToons1);
            model.addAttribute("newt2", randomToons2);
            model.addAttribute("newt3", randomToons3);
            model.addAttribute("hot1", hott1);
            model.addAttribute("hot2", hott2);
            model.addAttribute("hot3", hott3);
            model.addAttribute("upt1", randomUpToons1);
            model.addAttribute("upt2", randomUpToons2);
            model.addAttribute("upt3", randomUpToons3);
            String favoriteToons = user.getUSER_FAVORITE();
            if(favoriteToons != null && !favoriteToons.isEmpty())
            {
                String[] favoriteToonsContainer = favoriteToons.split(",");

                List<Toon> favoriteToonsList = new ArrayList<>();
                for(String favoriteToon: favoriteToonsContainer)
                {
                    Toon toon = toonService.getToonByTitle(favoriteToon);
                    if (toon != null) {
                        favoriteToonsList.add(toon);
                    }
                }
                List<Toon> favoriteToonForMain = toonService.getRandomallToons(favoriteToonsList, 9);
                model.addAttribute("favoriteToons", favoriteToonForMain);
            }

            String pick1 = user.getUSER_PICK1();
            String pick2 = user.getUSER_PICK2();
            String pick3 = user.getUSER_PICK3();

            // 각 카테고리에 해당하는 웹툰 정보를 가져오기
            List<Toon> toons1 = toonService.tagToonsByKeyword(pick1);
            List<Toon> toons2 = toonService.tagToonsByKeyword(pick2);
            List<Toon> toons3 = toonService.tagToonsByKeyword(pick3);

            // 각 카테고리에서 3개씩 랜덤으로 선택
            List<Toon> randomToons11 = toonService.getRandomToons1(toons1, 3);
            List<Toon> randomToons22 = toonService.getRandomToons2(toons2, 3);
            List<Toon> randomToons33 = toonService.getRandomToons3(toons3, 3);

            // 모델에 추가
            model.addAttribute("pick1Toons", randomToons11);
            model.addAttribute("pick2Toons", randomToons22);
            model.addAttribute("pick3Toons", randomToons33);

            model.addAttribute("user", user);

            return "main";
        } else {

            List<Toon> newtoon1 = toonService.newToon1();
            List<Toon> randomToons1 = toonService.getRandomToons1(newtoon1, 3);
            List<Toon> newtoon2 = toonService.newToon2();
            List<Toon> randomToons2 = toonService.getRandomToons2(newtoon2, 3);
            List<Toon> newtoon3 = toonService.newToon3();
            List<Toon> randomToons3 = toonService.getRandomToons3(newtoon3, 3);

            List<Toon> up1 = toonService.upToon1();
            List<Toon> randomUpToons1 = toonService.getRandomUpToons1(up1, 3);
            List<Toon> up2 = toonService.upToon2();
            List<Toon> randomUpToons2 = toonService.getRandomUpToons2(up2, 3);
            List<Toon> up3 = toonService.upToon3();
            List<Toon> randomUpToons3 = toonService.getRandomUpToons3(up3, 3);

            List<Toon> hott1 = toonService.hotToon1();
            List<Toon> hott2 = toonService.hotToon2();
            List<Toon> hott3 = toonService.hotToon3();

            // 이제 newToon1, newToon2, newToon3, hotToon1, hotToon2, hotToon3를 제외한 데이터를 가져올 수 있습니다.
            // 해당 데이터를 모델에 추가하여 Thymeleaf에서 사용할 수 있도록 합니다.

            model.addAttribute("newt1", randomToons1);
            model.addAttribute("newt2", randomToons2);
            model.addAttribute("newt3", randomToons3);

            model.addAttribute("hot1", hott1);
            model.addAttribute("hot2", hott2);
            model.addAttribute("hot3", hott3);

            model.addAttribute("upt1", randomUpToons1);
            model.addAttribute("upt2", randomUpToons2);
            model.addAttribute("upt3", randomUpToons3);

            model.addAttribute("user", user);
            return "main";
        }
    }

    @PostMapping("/updateCount")
    public ResponseEntity<Void> countUpdated(@RequestParam("category") String category, @RequestParam("user") String userId, @RequestParam("link") String link){
        System.out.println(category);
        System.out.println(userId);
        String genre = "";
        if(category.contains("#액션"))
        {
            genre= genre + "GENRE_ACTION;";
        }
        if(category.contains("#코미디/일상"))
        {
            genre=genre + "GENRE_COMEDY;";
        }
        if(category.contains("#로맨스"))
        {
            genre=genre+"GENRE_ROMANCE;";
        }
        if(category.contains("#판타지"))
        {
            genre=genre+"GENRE_FANTASY;";
        }
        if(category.contains("#드라마"))
        {
            genre=genre+"GENRE_DRAMA;";
        }
        if(category.contains("#학원"))
        {
            genre=genre+"GENRE_ACADEMY;";
        }
        if(category.contains("#스릴러"))
        {
            genre=genre+"GENRE_THRILLER;";
        }

        if(genre != null && !genre.isEmpty())
        {
            String[] genres = genre.split(";");
            for(String gen : genres)
            {
                System.out.println(gen);
                toonRecommandService.countUpdate(gen, userId);
            }

        }
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store").build();
    }

    //20230921 추가
    @GetMapping("/genre")
    public String genre(HttpSession session , Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && !"0000".equals(user.getUSER_ID())) {
            // 로그인된 사용자의 경우 메인으로 이동
            List<Toon> allToons = toonService.getAllToons();

            // allToons 리스트의 순서를 랜덤하게 변경
            Collections.shuffle(allToons);

            logger.info("allToons"+allToons); // 로그 메시지 추가
            model.addAttribute("toons", allToons);
            model.addAttribute("user", user);
            return "genre";
        } else {
            User guest = new User();
            guest.setUSER_ID("0000");
            guest.setUSER_PASSWORD("0000");
            guest.setUSER_FAVORITE("");
            guest.setUSER_LIKE("");
            guest.setUSER_NICKNAME("0000");
            guest.setUSER_MAIL("0000");
            guest.setUSER_NAME("0000");
            session.setAttribute("user", guest);
            // 로그인된 사용자의 경우 메인으로 이동
            List<Toon> allToons = toonService.getAllToons();

            // allToons 리스트의 순서를 랜덤하게 변경
            Collections.shuffle(allToons);

            logger.info("allToons"+allToons); // 로그 메시지 추가
            model.addAttribute("toons", allToons);
            model.addAttribute("user", guest);
            return "genre";
        }
    }

    //20231014 추가 - 전체조회 랜덤순
    @GetMapping("/selectToon")
    @ResponseBody
    public List<Toon> selectToon() {
        List<Toon> toons = toonService.searchToons();
        // allToons 리스트의 순서를 랜덤하게 변경
        Collections.shuffle(toons);
        return toons;
    }
    //20231014 추가 - 전체조회 이름순
    @GetMapping("/selectToonByName")
    @ResponseBody
    public List<Toon> selectToonByName() {
        List<Toon> toons = toonService.searchToonsByName();
        return toons;
    }

    // Controller에서 좋아요순으로 웹툰을 가져오는 API
    @GetMapping("/selectToonByLikes")
    @ResponseBody
    public List<Toon> selectToonByLikes() {
        List<Toon> toons = toonService.searchToonByLikes();
        return toons;
    }

    //20230921 검색기능 추가
    //20231014 추가 - uri 변경
    @GetMapping("/searchToonByRandom")
    @ResponseBody
    public List<Toon> searchToon(@RequestParam String keyword) {
        logger.info("searchToon이 타고있나요?"); // 로그 메시지 추가
        List<Toon> toons = toonService.searchToonsByKeyword(keyword);
        logger.info("toons : "+toons); // 로그 메시지 추가
        return toons;
    }

    //20231014 추가 - 태그 선택 이름순
    @GetMapping("/tagByname")
    @ResponseBody
    public List<Toon> byNameToon(@RequestParam String keyword) {
        List<Toon> toons = toonService.tagToonsByKeywordByName(keyword);
        logger.info("toons : "+toons); // 로그 메시지 추가
        return toons;
    }
    //20231014 추가 - 태그 선택 랜덤순
    @GetMapping("/tagByRandom")
    @ResponseBody
    public List<Toon> tagToon(@RequestParam String keyword) {
        List<Toon> toons = toonService.tagToonsByKeyword(keyword);
        Collections.shuffle(toons);
        return toons;
    }

    //태그 선택 좋아요순
    @GetMapping("/tagBylikes")
    @ResponseBody
    public List<Toon> byLikesToon(@RequestParam String keyword) {
        List<Toon> toons = toonService.tagToonsByKeywordByLikes(keyword);
        logger.info("toons : "+toons); // 로그 메시지 추가
        return toons;
    }


    @GetMapping("/user/pw")
    public String pw() {
        return "/user/pw";
    }

    public static String numberGen(int len, int dupCd) {
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
        for (int i = 0; i < len; i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            if (dupCd == 1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            } else if (dupCd == 2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if (!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                } else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i -= 1;
                }
            }
        }
        return numStr;
    }


    @GetMapping("/favorites")
    public ResponseEntity<Void> favoriteFunction(@RequestParam("ToonName") String ToonName, HttpSession session)
    {
        System.out.println(ToonName);
        User user = (User) session.getAttribute("user");
        if (user != null && !"0000".equals(user.getUSER_ID())) {
            User userData = userService.getUser(user.getUSER_ID());
            String userFavorite = userData.getUSER_FAVORITE();
            String dbFavorite = userData.getUSER_FAVORITE();
            if(userFavorite != null && !userFavorite.isEmpty())
            {
                if(userFavorite.contains(ToonName))
                {
                    dbFavorite= dbFavorite.replaceFirst(ToonName + ",", "");
                }
                else {
                    dbFavorite = dbFavorite + ToonName + ",";
                }
            }
            else
            {
                dbFavorite = ToonName + ",";
            }

            userService.setFavorites(user.getUSER_ID(), dbFavorite);
        }
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store").build();
    }

    @GetMapping("likes")
    public ResponseEntity<Void> likeFunction(@RequestParam("ToonName") String ToonName, HttpSession session)
    {
        System.out.println(ToonName);
        User user = (User) session.getAttribute("user");
        if (user != null && !"0000".equals(user.getUSER_ID())) {
            User userData = userService.getUser(user.getUSER_ID());
            String userLikes = userData.getUSER_LIKE();
            String dbLikes = userData.getUSER_LIKE();
            if(userLikes != null && !userLikes.isEmpty())
            {
                if(userLikes.contains(ToonName))
                {
                    dbLikes= dbLikes.replaceFirst(ToonName + ",", "");
                }
                else {
                    dbLikes = dbLikes + ToonName + ",";
                }
            }
            else
            {
                dbLikes = ToonName + ",";
            }

            userService.setLikes(user.getUSER_ID(), dbLikes);
        }
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store").build();
    }

}



