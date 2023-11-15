package jpashop.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpashop.jpashop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIgnore
    private List<Item> items = new ArrayList<>();
    //@ManyToMany -> 컬럼추가X , 세밀한 쿼리 실행X -> 실무 사용X
    //@OneToMany,@ManyToOne으로 중간 엔티티를 만들어 매핑
//    @OneToMany(mappedBy = "category")
//    private List<Category_Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    public void setChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
