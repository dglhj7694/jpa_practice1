package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;

	public void save(Item item) {
		if (item.getId() == null) {
			em.persist(item);
		} else {
			/*
			 * 준영속성 관리 
			 * merge (병합) 사용
			 * 병합시 모든 파라미터로 들어온 속성이 변경
			 * merge 대신 @Transactional (엔티티 변경감지 set..) 사용
			 * 엔티티를 변경할 때는 항상 변경 감지를 사용하세요.
			 */
			em.merge(item);
		}
	}

	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}

	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class)
				.getResultList();
	}
}
