package com.uahannam.member.repository;

import com.uahannam.member.dto.query.MemberDto;
import com.uahannam.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT new com.uahannam.member.dto.MemberDto(m.email, m.name, r.roleName, m.contact, m.nickname, m.balance) " +
            "FROM Member m LEFT JOIN m.role r")
    List<MemberDto> findAllMembersAsDto();

    @Query("select new com.uahannam.member.dto.MemberDto(m.email, m.name, r.roleName, m.contact, m.nickname, m.balance) " +
            "from Member m JOIN m.role r " +
            "where m.id = :memberId")
    MemberDto findMemberByIdAsDto(Integer memberId);

    boolean existsByEmail(String email);

    Member findByEmail(String email);
}
