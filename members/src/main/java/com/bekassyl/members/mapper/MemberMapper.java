package com.bekassyl.members.mapper;

import com.bekassyl.members.dto.MemberDto;
import com.bekassyl.members.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member toEntity(MemberDto memberDto);
    MemberDto toDto(Member member);
}
