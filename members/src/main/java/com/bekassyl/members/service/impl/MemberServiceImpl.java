package com.bekassyl.members.service.impl;

import com.bekassyl.members.dto.MemberDto;
import com.bekassyl.members.dto.MemberMsgDto;
import com.bekassyl.members.entity.Member;
import com.bekassyl.members.exception.ResourceNotFoundException;
import com.bekassyl.members.exception.MemberAlreadyExistsException;
import com.bekassyl.members.mapper.MemberMapper;
import com.bekassyl.members.repository.MemberRepository;
import com.bekassyl.members.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements IMemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private static final SecureRandom secureRandom = new SecureRandom();
    private final StreamBridge streamBridge;

    /**
     * Finds member details by cardNumber.
     *
     * @param cardNumber cardNumber to search for
     * @return DTO containing member details for the given cardNumber
     * @throws ResourceNotFoundException if a member is not found
     */
    @Override
    public MemberDto fetchMemberByCardNumber(String cardNumber) {
        Member member = memberRepository.findByCardNumber(cardNumber).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Card number", cardNumber)
        );

        return memberMapper.toDto(member);
    }

    /**
     * Finds member details by iin.
     *
     * @param iin iin to search for
     * @return DTO containing member details for the given iin
     * @throws ResourceNotFoundException if a member is not found
     */
    @Override
    public MemberDto fetchMemberByIin(String iin) {
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

        sendMemberCreated(member);

        return true;
    }

    private void sendMemberCreated(Member member) {
        MemberMsgDto memberMsgDto = new MemberMsgDto(
                member.getCardNumber(),
                String.format("%s %s", member.getFirstName(), member.getLastName()),
                member.getIin(),
                member.getMobileNumber(),
                member.getEmail()
        );

        log.info("Sending a request to the sendMemberCreated with details: {}", memberMsgDto);

        boolean result = streamBridge.send("sendMemberCreated-out-0", memberMsgDto);

        log.info("Is the request successfully triggered?: {}", result);
    }

    /**
     * Updates member details, excluding the card number, iin.
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
     * Deletes a member by cardNumber.
     *
     * @param cardNumber cardNumber to identify the member
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    @Transactional
    @Override
    public boolean deleteMemberByCardNumber(String cardNumber) {
        Member member = memberRepository.findByCardNumber(cardNumber).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Card number", cardNumber)
        );

        memberRepository.delete(member);

        return true;
    }

    /**
     * Deletes a member by iin.
     *
     * @param iin iin to identify the member
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    @Transactional
    @Override
    public boolean deleteMemberByIin(String iin) {
        Member member = memberRepository.findByIin(iin).orElseThrow(
                () -> new ResourceNotFoundException("Member", "IIN", iin)
        );

        memberRepository.delete(member);

        return true;
    }

    /**
     * Updates communication status
     *
     * @param iin to identify the member
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(String iin) {
        boolean isUpdated = false;

        if (iin != null && !iin.isEmpty()) {
            Member member = memberRepository.findByIin(iin).orElseThrow(
                    () -> new ResourceNotFoundException("Member", "IIN", iin)
            );

            member.setCommunicationStatus(true);
            memberRepository.save(member);

            isUpdated = true;
        }

        return isUpdated;
    }
}
