package com.bekassyl.members.service;

import com.bekassyl.members.dto.MemberDto;
import com.bekassyl.members.exception.ResourceNotFoundException;
import com.bekassyl.members.exception.MemberAlreadyExistsException;

public interface IMemberService {
    /**
     * Finds book details by isbn.
     *
     * @param iin iin to search for
     * @return DTO containing member details for the given iin
     * @throws ResourceNotFoundException if a member is not found
     */
    MemberDto fetchMember(String iin);

    /**
     * Saves a new member.
     *
     * @param memberDto member data transfer object
     * @throws MemberAlreadyExistsException if a member with the given iin already exists
     */
    boolean createMember(MemberDto memberDto);

    /**
     * Updates member details, excluding the iin, card number.
     *
     * @param memberDto DTO containing updated member information
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    boolean updateMember(MemberDto memberDto);

    /**
     * Deletes a member.
     *
     * @param iin iin to identify the member
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a member is not found
     */
    boolean deleteMember(String iin);
}
