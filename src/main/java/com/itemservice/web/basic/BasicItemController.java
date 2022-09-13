package com.itemservice.web.basic;

import com.itemservice.domain.item.Item;
import com.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //아래 주석처리된 private final 선언한 생성자를 알아서 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    // @Autowired // 생성자가있으면 생략가능
    // public BasicItemController(ItemRepository itemRepository) {
    //    this.itemRepository = itemRepository;
    // }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,  //@RequestParam 의 변수들은 html input Tag 의 name 속성에 따라서 매칭된다.
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        // model.addAttribute("item", item);
        // 자동 추가가 됨, 생략가능함 -> ModelAttribute 가 자동으로 model 에 넣어준다. 그 때 이름이 @ModelAttribute("item") 의 item 으로 넣어준다.
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {
        //Default 룰이 있다. @ModelAttribute 의 name 속성을 지우면
        //Item => item 의 이름을 자동으로 넣어준다.
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item, Model model) {
        //ModelAttribute 를 생략가능하지만, 추천하지 않는다.
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, Model model) {
        //Default 룰이 있다. @ModelAttribute 의 name 속성을 지우면
        //Item => item 의 이름을 자동으로 넣어준다.
        Item saveItem = itemRepository.save(item);
        return "redirect:/basic/items/" + saveItem.getId();
        // return "basic/item";
        // Redirect 를 하지 않으면 새로고침 시 같은 상품이 계속 등록이된다. (마지막에 요청한 URL 을 계속 호출하기 때문에)
    }

    @PostMapping("/add")
    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; // {itemId} 는 redirectAttribute.addAttribute 의 itemId 와 매칭된다. 매핑이 되지 않은 status 는 쿼리파라미터로 주입된다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; //@PathVariable long itemId 처럼 사용가능하다

    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
