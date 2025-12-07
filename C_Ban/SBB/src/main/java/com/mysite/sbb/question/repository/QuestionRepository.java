package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// 옛날엔 @Repository를 붙였지만 얘만 자동으로 올라오기떄문에 붙이지 않는다.
// JpaRepository<T, id> - JpaRepository: 상속받아서 사용, T: 내가 바라볼 엔티티, id: 기본키 타입(제네릭 타입은 클래스 타입만 가능)
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findBySubjectLike(String s); // QueryMethod로 동작 가능(한 테이블만 바라볼 떄만 가능, 조인 시 불가)

    @Query(value = "Select * From ", nativeQuery = true) // nativeQuery = true가 없으면 * 대신 클래스 작성
//    @Query(value = "Select a1 From Question a1 ")
    List<Question> abcd(Long id, String keyword);

    // command + shift + T로 Junit 테스트 생성가능

    Page<Question> findAll(Pageable pageable); // 리스트가 아닌 페이지로 들고 옴

    Page<Question> findAll(Specification<Question> specification, Pageable pageable);

    // jpql 방식
    @Query(value =
        "SELECT distinct q "
        + "FROM Question q "
        + "LEFT OUTER JOIN Member m1 ON q.author = m1 "
        + "LEFT OUTER JOIN Answer a ON a.question = q "
        + "LEFT OUTER JOIN Member m2 ON a.author = m2 "
        + "WHERE q.subject LIKE %:keyword% " // 가져온 변수로 인식시키기 위해 @Param, ':' 사용
        + "OR q.content LIKE %:keyword% "
        + "OR m1.username LIKE %:keyword% "
        + "OR a.content LIKE %:keyword% "
        + "OR m2.username LIKE %:keyword% "
        , nativeQuery = false
    )
    Page<Question> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
