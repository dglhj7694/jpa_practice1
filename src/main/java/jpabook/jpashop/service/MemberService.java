package jpabook.jpashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor : 생성자를 만들어주는 어노테이션
//@AllArgsConstructor 
// @RequiredArgsConstructor : final 필드만 생성자를 만들어줌
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// setter injection
	/*
	 * @Autowired public void setMemberRepository(MemberRepository memberRepository)
	 * { this.memberRepository = memberRepository; }
	 */

	// 생성자 injection
	/*
	 * public MemberService(MemberRepository memberRepository) {
	 * this.memberRepository = memberRepository; }
	 */

	// 회원가입
	@Transactional(readOnly = false)
	public Long join(Member member) {
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	// 회원 한명 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findOne(id);
		member.setName(name);
	}
}
