// Cart logic using localStorage for all pages
function getCart() {
  return JSON.parse(localStorage.getItem('cart') || '[]');
}
function setCart(cart) {
  localStorage.setItem('cart', JSON.stringify(cart));
}

function updateCartBadge() {
  var cart = getCart();
  var badge = document.getElementById('cartCountBadge');
  if (badge) {
    var totalItems = cart.reduce((sum, item) => sum + item.qty, 0);
    var oldText = badge.textContent;
    if (totalItems > 0) {
      badge.textContent = totalItems;
      badge.style.display = 'block';
      // Trigger bounce animation if quantity changed
      if (oldText !== String(totalItems)) {
        badge.classList.remove('badge-bounce');
        void badge.offsetWidth; // Trigger reflow
        badge.classList.add('badge-bounce');
      }
    } else {
      badge.style.display = 'none';
      badge.textContent = '0';
    }
  }
}

// Add animation styles dynamically if not present
if (!document.getElementById('cart-badge-styles')) {
  const style = document.createElement('style');
  style.id = 'cart-badge-styles';
  style.textContent = `
        @keyframes badgeBounce {
            0% { transform: scale(1); }
            40% { transform: scale(1.4); }
            60% { transform: scale(0.9); }
            100% { transform: scale(1); }
        }
        .badge-bounce {
            animation: badgeBounce 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
        }
        /* Customizing cart section for a premium look */
        #cartSection {
            backdrop-filter: blur(15px) !important;
            -webkit-backdrop-filter: blur(15px) !important;
            background: rgba(255, 255, 255, 0.85) !important;
            border-radius: 16px !important;
            border: 1px solid rgba(255,255,255,0.3) !important;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1) !important;
            padding: 24px !important;
            transition: all 0.3s ease !important;
        }
    `;
  document.head.appendChild(style);
}

function updateCartDisplay() {
  var cart = getCart();
  var cartItems = document.getElementById('cartItems');
  var cartSubtotal = document.getElementById('cartSubtotal');

  // Always update badge
  updateCartBadge();

  if (!cartItems) return;
  cartItems.innerHTML = '';
  let subtotal = 0;
  if (cart.length === 0) {
    cartItems.innerHTML = '<li>Your cart is empty.</li>';
    if (cartSubtotal) cartSubtotal.textContent = '';
    // Remove Proceed to Buy if present
    var proceedBtn = document.getElementById('proceedToBuyBtn');
    if (proceedBtn) proceedBtn.remove();
  } else {
    cart.forEach(function (item, idx) {
      subtotal += item.price * item.qty;
      var li = document.createElement('li');
      li.style.display = 'flex';
      li.style.alignItems = 'center';
      li.style.marginBottom = '10px';
      li.innerHTML = `
        <img src="${item.img}" alt="${item.name}" style="width:50px;height:50px;object-fit:cover;margin-right:10px;border:1px solid #ccc;">
        <div style="flex:1;">
          <div style="font-weight:600;">${item.name}</div>
          <div style="color:#555;">₹${item.price.toLocaleString()}</div>
          <div style="font-size:13px;">Qty: 
            <button onclick="updateQty(${idx},-1)" style='width:22px;'>-</button>
            <span style='margin:0 6px;'>${item.qty}</span>
            <button onclick="updateQty(${idx},1)" style='width:22px;'>+</button>
            <button onclick="removeFromCart(${idx})" style='margin-left:10px;color:#e74c3c;background:none;border:none;cursor:pointer;'>Delete</button>
          </div>
        </div>
      `;
      cartItems.appendChild(li);
    });
    if (cartSubtotal) cartSubtotal.textContent = `Subtotal (${cart.reduce((a, b) => a + b.qty, 0)} items): ₹${subtotal.toLocaleString()}`;
    // Add Proceed to Buy button if not present
    if (!document.getElementById('proceedToBuyBtn')) {
      var proceedBtn = document.createElement('button');
      proceedBtn.id = 'proceedToBuyBtn';
      proceedBtn.textContent = 'Proceed to Buy';
      proceedBtn.style.background = '#ffd814';
      proceedBtn.style.color = '#222';
      proceedBtn.style.border = 'none';
      proceedBtn.style.padding = '10px 24px';
      proceedBtn.style.fontWeight = '600';
      proceedBtn.style.fontSize = '1rem';
      proceedBtn.style.borderRadius = '4px';
      proceedBtn.style.cursor = 'pointer';
      proceedBtn.style.marginTop = '10px';
      proceedBtn.onclick = function () {
        window.location.href = 'checkout.html';
      };
      cartItems.parentNode.appendChild(proceedBtn);
    }
  }
}
function updateQty(idx, delta) {
  var cart = getCart();
  cart[idx].qty += delta;
  if (cart[idx].qty < 1) cart[idx].qty = 1;
  setCart(cart);
  updateCartDisplay();
}
function removeFromCart(idx) {
  var cart = getCart();
  cart.splice(idx, 1);
  setCart(cart);
  updateCartDisplay();
}
window.updateQty = updateQty;
window.removeFromCart = removeFromCart;

function addToCart(item) {
  var cart = getCart();
  var found = cart.find(i => i.name === item.name);
  if (found) {
    found.qty += item.qty;
  } else {
    cart.push(item);
  }
  setCart(cart);
  updateCartDisplay();
}
window.addToCart = addToCart;
window.updateCartDisplay = updateCartDisplay;
window.updateCartBadge = updateCartBadge;

document.addEventListener('DOMContentLoaded', function () {
  var cartIcon = document.getElementById('cartIcon');
  var cartSection = document.getElementById('cartSection');
  var closeCart = document.getElementById('closeCart');

  // Initial badge update
  updateCartBadge();

  if (cartIcon && cartSection) {
    cartIcon.onclick = function () {
      updateCartDisplay();
      cartSection.style.display = 'block';
    };
  }
  if (closeCart && cartSection) {
    closeCart.onclick = function () {
      cartSection.style.display = 'none';
    };
  }
});
