package com.bekassyl.members.service;

import com.bekassyl.members.dto.MemberDto;
import com.bekassyl.members.exception.ResourceNotFoundException;
import com.bekassyl.members.exception.MemberAlreadyExistsException;

public interface IMemberService {
    /**
     * Finds member details by cardNumber.
     *
     * @param cardNumber cardNumber to search for
     * @return DTO containing member details for the given cardNumber
     * @throws ResourceNotFoundException if a member is not found
     */
    MemberDto fetchMemberByCardNumber(String cardNumber);

    /**
     * Finds member details by iin.
     *
     * @param iin iin to search for
     * @return DTO containing member details for the given iin
     * @throws ResourceNotFoundException if a member is not found
     */
    MemberDto fetchMemberByIin(String iin);

    /**
     * Saves a new member.
     *
     * @param memberDto member data transfer object
     * @throws MemberAlreadyExistsException if a member with the given iin already exists
     */
    boolean createMember(MemberDto memberDto);

    /**
     * Updates member details, excluding the card number, iin.
     *
     * @param memberDto DTO containing updated member information
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    boolean updateMember(MemberDto memberDto);

    /**
     * Deletes a member by cardNumber.
     *
     * @param cardNumber cardNumber to identify the member
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    boolean deleteMemberByCardNumber(String cardNumber);

    /**
     * Deletes a member by iin.
     *
     * @param iin iin to identify the member
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    boolean deleteMemberByIin(String iin);
}
