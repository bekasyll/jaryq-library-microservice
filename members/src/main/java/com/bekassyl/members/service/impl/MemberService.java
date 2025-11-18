package com.bekassyl.members.service.impl;

import com.bekassyl.members.dto.MemberDto;
import com.bekassyl.members.entity.Member;
import com.bekassyl.members.exception.ResourceNotFoundException;
import com.bekassyl.members.exception.MemberAlreadyExistsException;
import com.bekassyl.members.mapper.MemberMapper;
import com.bekassyl.members.repository.MemberRepository;
import com.bekassyl.members.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements IMemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Finds book details by isbn.
     *
     * @param iin iin to search for
     * @return DTO containing member details for the given iin
     * @throws ResourceNotFoundException if a member is not found
     */
    @Override
    public MemberDto fetchMember(String iin) {
        Member member = memberRepository.findByIin(iin).orElseThrow(
                () -> new ResourceNotFoundException("Member", "IIN", iin)
        );

        return memberMapper.toDto(member);
    }

    /**
     * Saves a new member.
     *
     * @param memberDto member data transfer object
     * @throws MemberAlreadyExistsException if a member with the given iin already exists
     */
    @Transactional
    @Override
    public boolean createMember(MemberDto memberDto) {
        if (memberRepository.findByIin(memberDto.getIin()).isPresent()) {
            throw new MemberAlreadyExistsException("Member with IIN " + memberDto.getIin() + " already exists");
        }

        String cardNumber;
        do {
            cardNumber = String.valueOf(1000_0000_0000L + secureRandom.nextLong(8999_9999_9999L));
        } while (memberRepository.existsByCardNumber(cardNumber));

        Member member = memberMapper.toEntity(memberDto);
        member.setCardNumber(cardNumber);

        memberRepository.save(member);

        return true;
    }

    /**
     * Updates member details, excluding the iin, card number.
     *
     * @param memberDto DTO containing updated member information
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    @Transactional
    @Override
    public boolean updateMember(MemberDto memberDto) {
        Member member = memberRepository.findByIin(memberDto.getIin()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "IIN", memberDto.getIin())
        );

        member.setFirstName(memberDto.getFirstName());
        member.setLastName(memberDto.getLastName());
        member.setEmail(memberDto.getEmail());
        member.setMobileNumber(memberDto.getMobileNumber());
        member.setAddress(memberDto.getAddress());

        memberRepository.save(member);

        return true;
    }

    /**
     * Deletes a member.
     *
     * @param iin iin to identify the member
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    @Transactional
    @Override
    public boolean deleteMember(String iin) {
        Member member = memberRepository.findByIin(iin).orElseThrow(
                () -> new ResourceNotFoundException("Member", "IIN", iin)
        );

        memberRepository.delete(member);

        return true;
    }
}
