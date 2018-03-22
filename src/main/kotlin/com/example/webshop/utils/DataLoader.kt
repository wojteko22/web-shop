package com.example.webshop.utils

// todo: tej klasy bym nie nazwał utilem,
// jak macie jakiś pomysł to można zmienić

import com.example.webshop.entity.Category
import com.example.webshop.entity.Shop
import com.example.webshop.repository.CategoryRepository
import com.example.webshop.repository.ShopRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader {

    @Bean
    fun initCategories(repository: CategoryRepository) = CommandLineRunner {
        val pieczywo = Category("Pieczywo")
        val nabial = Category("Nabial")
        val sery = Category("Sery", nabial)
        val maslo = Category("Maslo", nabial)
        val mleko = Category("Mleko", nabial)
        val bialySer = Category("Biale", sery)
        val zoltySer = Category("Zolte", sery)

        repository.save(pieczywo)
        repository.save(nabial)
        repository.save(sery)
        repository.save(maslo)
        repository.save(mleko)
        repository.save(bialySer)
        repository.save(zoltySer)
    }

    @Bean
    fun init(repository: ShopRepository) = CommandLineRunner {
        repository.save(Shop("Żabcia", "Wrocław", "Grunwaldzka", "50-387"))
        repository.save(Shop("Biedroneczka", "Wrocław", "Piękna", "51-388"))
    }
}