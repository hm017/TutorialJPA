package com.techacademy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("country")
public class CountryController {
    private final CountryService service;

    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    // ----- 一覧画面 -----
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("countrylist", service.getCountryList());
        // country/list.htmlに画面遷移
        return "country/list";
    }

    // ----- 詳細画面 -----
    @GetMapping(value = { "/detail", "/detail/{code}/" })
    public String getCountry(@PathVariable(name = "code", required = false) String code, Model model) {
        //new Country()　→　空のオブジェク
        Country country = code != null ? service.getCountry(code) : new Country();
        model.addAttribute("country", country);
        return "country/detail";
    }

    // ----- 更新（追加） -----
    @PostMapping("/detail")
    public String postCountry(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("population") int population, Model model) {
        service.updateCountry(code, name, population);
        return "redirect:/country/list";
    }

    // ----- 削除画面 -----
    @GetMapping(value = { "/delete", "/delete/{code}/" })
    public String deleteCountryForm(@PathVariable(name = "code", required = false) String code, Model model) {
        model.addAttribute("code", code);
        return "country/delete";
    }

    // ----- 削除 -----
    @PostMapping("/delete")
    public String deleteCountry(@RequestParam("code") String code, Model model) {
        service.deleteCountry(code);
        return "redirect:/country/list";
    }
}