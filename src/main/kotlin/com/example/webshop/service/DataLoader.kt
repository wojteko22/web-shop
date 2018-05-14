package com.example.webshop.service

import com.example.webshop.entity.*
import com.example.webshop.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class DataLoader {

    private val owner = UserRole("SHOP_OWNER", "Rola sprzedawcy....", 1)
    private val admin = UserRole(Role.ADMIN.name, "Rola administratora....", 2)
    private val customer = UserRole("CUSTOMER", "Rola klienta sklepu...", 3)

    @Bean
    fun initRoles(roleRepository: UserRoleRepository) = CommandLineRunner {
        val seller = UserRole("SELLER", "Rola sprzedawcy....", 4)
        roleRepository.save(owner)
        roleRepository.save(admin)
        roleRepository.save(customer)
        roleRepository.save(seller)
    }

    private val owner1 = User("test@test.pl", BCryptPasswordEncoder().encode("test"), "Jan Nowak", owner, 1)
    private val owner2 = User("test2@test.pl", BCryptPasswordEncoder().encode("test"), "Jan Zdzislaw", owner, 2)
    private val owner3 = User("test3@test.pl", BCryptPasswordEncoder().encode("test"), "Jakub Rolnik", owner, 3)
    private val owner4 = User("test4@test.pl", BCryptPasswordEncoder().encode("test"), "Bartosz Wodnik", owner, 4)
    private val owner5 = User("test5@test.pl", BCryptPasswordEncoder().encode("test"), "Janusz Swawolny", owner, 5)
    private val owner6 = User("test6@test.pl", BCryptPasswordEncoder().encode("test"), "Milosz Laty", owner, 6)
    private val owner7 = User("test7@test.pl", BCryptPasswordEncoder().encode("test"), "Mikolaj Wasik", owner, 7)

    @Bean
    fun initUser(userRepository: UserRepository) = CommandLineRunner {
        userRepository.save(owner1)
        userRepository.save(owner2)
        userRepository.save(owner3)
        userRepository.save(owner4)
        userRepository.save(owner5)
        userRepository.save(owner6)
        userRepository.save(owner7)
        val admin1 = User("admin@test.pl", BCryptPasswordEncoder().encode("test"), "Ada Adminka", admin, 8)
        userRepository.save(admin1)
        val user = User("user@test.pl", BCryptPasswordEncoder().encode("test"), "Ada Adminka", customer, 9)
        userRepository.save(user)
    }

    private val shop1 = Shop("Żabcia", "Wrocław", "Grunwaldzka", "50-387", owner1, id = 1)
    private val shop2 = Shop("Biedronka", "Wrocław", "Polna", "50-387", owner2, id = 2)


    @Bean
    fun initShop(shopRepository: ShopRepository) = CommandLineRunner {
        val shop3 = Shop("Bartosz", "Wrocław", "Baciarellego", "50-387", owner3)
        val shop4 = Shop("Ikea", "Wrocław", "Miła", "50-387", owner4)
        val shop5 = Shop("Mila", "Wrocław", "Kolorowa", "50-387", owner5)
        val shop6 = Shop("Kasa", "Wrocław", "Norwida", "50-387", owner6)
        val shop7 = Shop("Maza", "Wrocław", "Wajdy", "50-387", owner7)
        shopRepository.save(shop1)
        shopRepository.save(shop2)
        shopRepository.save(shop3)
        shopRepository.save(shop4)
        shopRepository.save(shop5)
        shopRepository.save(shop6)
        shopRepository.save(shop7)
    }

    private val pieczywoShop1 = Category("Pieczywo", shop1)
    private val pieczywoShop2 = Category("Pieczywo", shop2)
    private val alkohole = Category("Alkohole", shop1)

    @Bean
    fun initCategories(repository: CategoryRepository, shopRepository: ShopRepository) = CommandLineRunner {
        val nabial = Category("Nabial", shop1)
        val sery = Category("Sery", shop1, nabial)
        val maslo = Category("Maslo", shop1, nabial)
        val mleko = Category("Mleko", shop1, nabial)
        val bialySer = Category("Biale", shop1, sery)
        val zoltySer = Category("Zolte", shop1, sery)
        val piwa = Category("Piwa", shop1, alkohole)

        repository.save(pieczywoShop1)
        repository.save(pieczywoShop2)
        repository.save(alkohole)
        repository.save(nabial)
        repository.save(sery)
        repository.save(maslo)
        repository.save(mleko)
        repository.save(bialySer)
        repository.save(zoltySer)
        repository.save(piwa)
    }

    private val product1 = Product("Heineken", 3, "unit", "status", "description", "http://static.wirtualnemedia.pl/media/images/2013/images/warka-classic-nowaszata-2.png", alkohole, shop1, 1)
    private val product2 = Product("Warka", 2, "unit", "status", "description", "http://static.wirtualnemedia.pl/media/images/2013/images/warka-classic-nowaszata-2.png ", alkohole, shop1, 2)
    private val product3 = Product("Bułka żytnia", 4, "1 szt", "status", "description", "https://img.e-piotripawel.pl/photos/hd9/49101.jpg", pieczywoShop1, shop1, 3)
    private val product4 = Product("Bułka pszenna", 1, "1 szt - 100 g", "status", "description", "http://www.delifrance.com/media/catalog/product/cache/9/image/9df78eab33525d08d6e5fb8d27136e95/S/0/S0695_S5896.jpg", pieczywoShop1, shop1, 4)
    private val product5 = Product("Piwo żytnie", 5, "500ml", "status", "description", "https://res.cloudinary.com/dj484tw6k/f_auto,q_auto,c_pad,b_white,w_360,h_360/v1500521344/bb/99631.png", alkohole, shop1, 5)
    private val product6 = Product("Piwo pszenne", 10, "500ml", "status", "description", "http://ocen-piwo.pl/upload/witnica-pszeniczne.png", alkohole, shop1, 6)
    private val product7 = Product("Chleb pszenny", 5, "600g", "status", "description", "https://akademiasmaku.pl/upload/recipes/13/big/chleb-pszenny-na-zakwasie-z-karmelizowana-cebulka-13.JPG", pieczywoShop1, shop1, 7)
    private val product8 = Product("Chleb żytni", 7, "700g", "status", "description", "http://jamiprzepisy.pl/wp-content/uploads/2016/06/chleb_zytni_1_1400.jpg", pieczywoShop1, shop1, 8)
    private val product9 = Product("Rogal", 3, "150g", "status", "description", "https://upload.wikimedia.org/wikipedia/commons/7/73/Rogalik.jpg", pieczywoShop1, shop1, 9)
    private val product10 = Product("Drożdzówka", 2, "300g", "status", "description", "http://www.sumarex.pl/upload/oferta/drozdzowka_z_budyniem.jpg", pieczywoShop1, shop1, 10)
    private val product11 = Product("Drożdzówka", 2, "300g", "status", "description", "http://www.sumarex.pl/upload/oferta/drozdzowka_z_budyniem.jpg", pieczywoShop2, shop2, 11)
    private val product12 = Product("Drożdzówka", 2, "300g", "status", "description", "http://www.sumarex.pl/upload/oferta/drozdzowka_z_budyniem.jpg", pieczywoShop2, shop2, 12)

    @Bean
    fun initProducts(repository: ProductRepository) = CommandLineRunner {
        repository.save(product1)
        repository.save(product2)
        repository.save(product3)
        repository.save(product4)
        repository.save(product5)
        repository.save(product6)
        repository.save(product7)
        repository.save(product8)
        repository.save(product9)
        repository.save(product10)
        repository.save(product11)
        repository.save(product12)

        //haystack sphinx
    }

    private val order1 = Order("przyjęte", 1, shop1, owner2)
    private val order2 = Order("przyjęte", 2, shop1, owner2)

    @Bean
    fun initOrders(repository: OrderRepository) = CommandLineRunner {
        repository.save(order1)
        repository.save(order2)
    }

    @Bean
    fun initOrderPositions(repository: OrderPositionRepository) = CommandLineRunner {
        val orderPosition1 = OrderPosition(order1, product1, 3)
        val orderPosition2 = OrderPosition(order1, product2, 2)
        val orderPosition3 = OrderPosition(order2, product1, 1)
        repository.save(orderPosition1)
        repository.save(orderPosition2)
        repository.save(orderPosition3)
    }
}