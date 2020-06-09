




    $(window).scroll(function(){
    $('nav').toggleClass('scrolled', $(this).scrollTop() > 20);
});

$(window).scroll(function(){
    $('a').toggleClass('scrolled', $(this).scrollTop() > 20);
});

