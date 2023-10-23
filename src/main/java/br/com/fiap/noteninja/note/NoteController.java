package br.com.fiap.noteninja.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/notes")
public class NoteController {
    
    @Autowired
    NoteService noteService;

    @Autowired
    MessageSource messageSource;

    @GetMapping // ViewResolver
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("notes", noteService.findAll());
        return "notes/index";
    }

    @GetMapping("new")
    public String form(Note note){
        return "notes/form";
    }

    @PostMapping
    public String create(@Valid Note note, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) return "notes/form";
        noteService.save(note);
        redirect.addFlashAttribute("success", getMessage("notes.create.success"));
        return "redirect:/notes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if(noteService.delete(id)){
            redirect.addFlashAttribute("success", getMessage("notes.delete.success"));
        } else {
            redirect.addFlashAttribute("error", getMessage("notes.notfound.error"));
        }
        return "redirect:/notes";
    }

    private String getMessage(String code){
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
