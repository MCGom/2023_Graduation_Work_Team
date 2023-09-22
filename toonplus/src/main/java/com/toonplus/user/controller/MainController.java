package com.toonplus.user.controller;

import com.toonplus.email.dto.EmailVO;
import com.toonplus.email.service.EmailServiceImpl;
import com.toonplus.toon.dto.Toon;
import com.toonplus.toon.service.ToonServiceImpl;
import com.toonplus.user.dto.User;
import com.toonplus.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
        return "redirect:/main";
    }

    @GetMapping("/user/update")  //회원정보 수정 화면 띄우기
    public String update() {

        return "/user/updateForm";
    }

    @GetMapping("/user/mypage")  //회원정보 수정 화면 띄우기
    public String mypage() {

        return "/user/myPage";
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
    public String main(HttpSession session , Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 로그인된 사용자의 경우 메인으로 이동
            List<Toon> allToons = toonService.getAllToons();
            logger.info("allToons"+allToons); // 로그 메시지 추가
            model.addAttribute("toons", allToons); //20230920추가

            return "main";
        } else {
            // 로그인되지 않은 사용자의 경우 로그인 페이지로 이동
            return "redirect:/user/loginForm";
        }
    }

    //20230921 추가
    @GetMapping("/genre")
    public String genre(HttpSession session , Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 로그인된 사용자의 경우 메인으로 이동
            List<Toon> allToons = toonService.getAllToons();

            // allToons 리스트의 순서를 랜덤하게 변경
            Collections.shuffle(allToons);

            logger.info("allToons"+allToons); // 로그 메시지 추가
            model.addAttribute("toons", allToons);
            return "genre";
        } else {
            // 로그인되지 않은 사용자의 경우 로그인 페이지로 이동
            return "redirect:/user/loginForm";
        }
    }

    //20230921 검색기능 추가
    @GetMapping("/searchToon")
    @ResponseBody
    public List<Toon> searchToon(@RequestParam String keyword) {
        logger.info("searchToon이 타고있나요?"); // 로그 메시지 추가
        List<Toon> toons = toonService.searchToonsByKeyword(keyword);
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


}



