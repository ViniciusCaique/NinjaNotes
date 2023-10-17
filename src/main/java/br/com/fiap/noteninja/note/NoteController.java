package br.com.fiap.noteninja.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
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

    @GetMapping // ViewResolver
    public String index(Model model){
        model.addAttribute("notes", noteService.findAll());
        return "notes/index";
    }

    @GetMapping("new")
    public String form(){
        return "notes/form";
    }

    @PostMapping
    public String create(@Valid Note note, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) return "notes/form";
        noteService.save(note);
        redirect.addFlashAttribute("success", "Tarefa cadastrada com sucesso");
        return "redirect:/notes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if(noteService.delete(id)){
            redirect.addFlashAttribute("success", "nota apagada com sucesso");
        } else {
            redirect.addFlashAttribute("error", "nota nao encontrada");
        }
        return "redirect:/notes";
    }
}
