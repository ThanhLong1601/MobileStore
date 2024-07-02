package com.ex.mobilestore.service.impl;

import com.ex.mobilestore.constant.CartStatus;
import com.ex.mobilestore.dto.CartDto;
import com.ex.mobilestore.entity.CartEntity;
import com.ex.mobilestore.entity.CartProductEntity;
import com.ex.mobilestore.entity.ProductEntity;
import com.ex.mobilestore.entity.UserEntity;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.mapper.CartMapper;
import com.ex.mobilestore.repository.CartProductRepository;
import com.ex.mobilestore.repository.CartRepository;
import com.ex.mobilestore.repository.ProductRepository;
import com.ex.mobilestore.repository.UserRepository;
import com.ex.mobilestore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final CartProductRepository cartProductRepository;


    @Override
    public List<CartDto> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toCartDTO)
                .toList();
    }

    @Override
    public CartDto removeProductFromCart(Integer productId) {
        UserEntity user = getCurrentUser();
        CartEntity cartEntity = findOrCreateCartByUserId(user.getId());
        // Tìm sản phẩm trong giỏ hàng dựa trên productId
        Optional<CartProductEntity> cartProductOptional = cartEntity.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().equals(productId))
                .findFirst();

        if (cartProductOptional.isPresent()) {
            CartProductEntity cartProductToRemove = cartProductOptional.get();
            cartEntity.getCartProducts().remove(cartProductToRemove);

            cartProductRepository.delete(cartProductToRemove);

            updateCartTotalQuantity(cartEntity);
            updateCartTotalPrice(cartEntity);
            updateStatusCart(cartEntity, CartStatus.ORDERING);

            CartEntity updatedCart = cartRepository.save(cartEntity);

            return cartMapper.toCartDTO(updatedCart);
        } else {
            throw new NotFoundException(productId);
        }
    }

    @Override
    public CartDto addProductToCart(Integer productId) {

        UserEntity user = getCurrentUser();
        CartEntity cartEntity = findOrCreateCartByUserId(user.getId());

        // Tìm sản phẩm trong giỏ hàng dựa trên productId
        Optional<CartProductEntity> existingProduct = cartEntity.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().equals(productId))
                .findFirst();

        // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng và giá sản phẩm
        if (existingProduct.isPresent()) {
            CartProductEntity cartProductEntity = existingProduct.get();
            cartProductEntity.setQuantity(cartProductEntity.getQuantity() + 1); // Tăng số lượng lên nếu sản phẩm đã tồn tại
            // Cập nhật giá của sản phẩm dựa trên số lượng mới
            BigDecimal productPrice = cartProductEntity.getProduct().getPrice();
            int quantity = cartProductEntity.getQuantity();
            BigDecimal cartTotalPrice = productPrice.multiply(new BigDecimal(quantity));
            cartProductEntity.setCartPrice(cartTotalPrice);

            // cập nhật số lượng và số tiền cart product
            cartProductRepository.save(cartProductEntity);
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng thì tạo mới và thêm vào giỏ hàng
            ProductEntity productEntity = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException(productId));
            CartProductEntity cartProduct = new CartProductEntity();
            cartProduct.setCart(cartEntity);
            cartProduct.setProduct(productEntity);
            cartProduct.setQuantity(1); // Số lượng mặc định của sản phẩm là 1
            cartProduct.setCartPrice(productEntity.getPrice()); // Giá của sản phẩm trong giỏ hàng
            // thêm mới cart product
            CartProductEntity cartProductSaved = cartProductRepository.save(cartProduct);

            cartEntity.getCartProducts().add(cartProductSaved);
        }

        // Cập nhật lại tổng giá của giỏ hàng khi thêm hoặc cập nhật sản phẩm
        updateCartTotalPrice(cartEntity);
        updateStatusCart(cartEntity, CartStatus.ORDERING); // Cập nhật trạng thái của giỏ hàng
        CartEntity updateCart = cartRepository.save(cartEntity);
        return cartMapper.toCartDTO(updateCart);
    }

    @Override
    public void clearCart() {
        cartRepository.deleteAll();
    }


    // Phương thức lấy user đang đăng nhập hiện tại
    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(username));
    }

    // Phương thức tìm hoặc tạo mới giỏ hàng theo userID
    private CartEntity findOrCreateCartByUserId(Integer userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ORDERING).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            newCart.setUser(userEntity);
            newCart.setTotalProduct(BigDecimal.ZERO);
            newCart.setStatus(CartStatus.ORDERING);
            return cartRepository.save(newCart);
        });
    }

    // Phương thức cập nhật tổng giá của giỏ hàng
    private void updateCartTotalPrice(CartEntity cart) {

        BigDecimal total = cart.getCartProducts().stream()
                .map(cp -> cp.getCartPrice().multiply(BigDecimal.valueOf(cp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalProduct(total); // Cập nhật lại giá của giỏ hàng sau khi tính
    }

    //Phương thức cập nhật lại số lượng của giỏ hàng
    private void updateCartTotalQuantity(CartEntity cartEntity) {
        int totalQuantity = cartEntity.getCartProducts().stream()
                .mapToInt(CartProductEntity::getQuantity)
                .sum();
        cartEntity.setTotalProduct(BigDecimal.valueOf(totalQuantity));
    }

    //Phương thức cập nhật status
    private void updateStatusCart(CartEntity cartEntity, CartStatus status){
        cartEntity.setStatus(status);
    }
}
