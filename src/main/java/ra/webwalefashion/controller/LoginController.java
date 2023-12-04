package ra.webwalefashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.webwalefashion.DTO.request.UserLoginReq;
import ra.webwalefashion.DTO.response.UserLoginRes;
import ra.webwalefashion.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login-account")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @GetMapping
    public String login(Model model) {
        model.addAttribute("userLogin", new UserLoginReq());
        return "login";
    }

    @PostMapping
    public String loginPost(@Valid @ModelAttribute("userLogin") UserLoginReq userLoginReq, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        System.out.println(bindingResult.hasErrors());
        if (!bindingResult.hasErrors()) {
            UserLoginRes userLoginRes = userService.login(userLoginReq);
            if (userLoginRes != null) {
                if (userLoginRes.isStatus()) {
                    if (userLoginRes.getRole_id() == 1) {
                        redirectAttributes.addFlashAttribute("notification", "sd");
                        session.setAttribute("UserLoginSession", userLoginRes);
                        return "redirect:/admin";
                    } else {
                        redirectAttributes.addFlashAttribute("notification", "sd");
                        session.setAttribute("UserLoginSession", userLoginRes);
                        return "redirect:/";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("notifications", "sd");
                    return "redirect:/login-account";
                }
            } else {
                redirectAttributes.addFlashAttribute("thong", "sd");
                System.out.println("d");
                return "redirect:/login-account";
            }
        }
        return "login";
    }
}
