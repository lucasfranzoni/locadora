package br.edu.usj.ads.pw.locadora;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FilmeController {
    
    @Autowired
    private FilmeRepository repository;

    @GetMapping()
    public ModelAndView index(Boolean filmeExcluido) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("filmeExcluido", filmeExcluido);
        return modelAndView;
    } 

    @GetMapping("/consultar")
    public ModelAndView consultar(@RequestParam String nome, Boolean filmeExcluido) {
        List<Filme> filmes = repository.findByNomeContainsIgnoreCase(nome);
        ModelAndView modelAndView = new ModelAndView("consultar");
        modelAndView.addObject("nome", nome);
        modelAndView.addObject("filmes", filmes);
        modelAndView.addObject("filmeExcluido", filmeExcluido);
        return modelAndView;
    }
    
    @GetMapping("/detalhar/{id}")
    public Object detalhar(@PathVariable Long id, Boolean novoFilme, Boolean filmeAlterado) {
        Optional<Filme> filme = repository.findById(id);
        if(filme.isEmpty()) {
            return "redirect:/erro";
        }
        ModelAndView modelAndView = new ModelAndView("detalhar");
        modelAndView.addObject("filme", filme.get()) ;
        modelAndView.addObject("novoFilme", novoFilme);
        modelAndView.addObject("filmeAlterado", filmeAlterado);
        return modelAndView;
    }

    @GetMapping("/adicionar")
    public ModelAndView adicionar() {
        return new ModelAndView("adicionar");
    }

    @PostMapping("/adicionar")
    public String salvar(Filme filme, RedirectAttributes redirectAttributes) {
        filme.setQuantidadeDisponivel(filme.getQuantidadeTotal());
        repository.save(filme);
        Boolean novoFilme = true;
        redirectAttributes.addAttribute("novoFilme", novoFilme);
        return "redirect:/detalhar/" + filme.getId();
    }

    @GetMapping("/editar/{id}")
    public Object editar (@PathVariable Long id) {
        Optional<Filme> filme = repository.findById(id);
        if(filme.isEmpty()) {
            return "redirect:/erro";
        }
        ModelAndView modelAndView = new ModelAndView("editar");
        modelAndView.addObject("filme", filme.get());
        return modelAndView;
    }

    @PostMapping("/editar/{id}")
    public String salvar(@PathVariable Long id, Filme filme, RedirectAttributes redirectAttributes) {
        if(repository.existsById(id)) {
            repository.save(filme);
            Boolean filmeAlterado = true;
            redirectAttributes.addAttribute("filmeAlterado", filmeAlterado);
            return "redirect:/detalhar/" + filme.getId();
        } else {
            return "redirect:/erro";
        }
        
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, String ultimaPagina, String nome, RedirectAttributes redirectAttributes) {
        Optional<Filme> Optionalfilme = repository.findById(id);
        if(Optionalfilme.isEmpty()) {
            return "redirect:/erro";
        }
        repository.deleteById(id);
        Boolean filmeExcluido = true;
        redirectAttributes.addAttribute("filmeExcluido", filmeExcluido);
        if(ultimaPagina.equalsIgnoreCase("consultar")) {
            redirectAttributes.addAttribute("nome", nome);
            return "redirect:/consultar";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/erro")
    public ModelAndView erro() {
        return new ModelAndView("erro");
    }

}
