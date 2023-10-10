package br.com.fiap.noteninja.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



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
