package jpabook.jpashop.Controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
	private final MemberService memberService;

	@RequestMapping("/")
	public String home() {
		log.info("home controller");
		return "home";
	}

	@PostMapping("/members/new")
	public String create(@Valid MemberForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "members/createMemberForm";
		}
		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);

		memberService.join(member);

		return "redirect:/";
	}
}
