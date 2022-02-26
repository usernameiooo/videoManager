!
function(e) {
    "use strict";
    var n = {
        topPositon: 0,
        init: function() {
            n.getScrollbarWidth(),
            n.parallax(),
            n.background__image(),
            n.img__to_svg(),
            n.title__animation(),
            n.navigation__scroll(),
            n.resume__hover(),
            n.anchor__jump(),
            n.service__actions(),
            n.portfolio__category_filter(),
            n.contact__submit(),
            n.popup__actions(),
            n.navigation__resize(),
            n.navigation__closer(),
            n.navigation__mobile(),
            n.slider__initalize()
        },
        slider__title_animation: function() {
            e(".main_slider .title_holder h3").each(function() {
                var n = e(this);
                if (!n.hasClass("js_ready")) {
                    var i = n.text(),
                    t = "";
                    e.each(i.split(""),
                    function(e, n) {
                        0 === e && (t += '<span class="word">'),
                        t += " " === n ? '</span>&nbsp<span class="word">': '<span class="char">' + n + "</span>"
                    }),
                    n.html(t)
                }
                n.addClass("js_ready")
            })
        },
        slider__initalize: function() {
            n.slider__title_animation(),
            n.background__image(),
            n.img__to_svg(),
            e(".main_slider").each(function() {
                var i = e(this).find(".swiper-container");
                new Swiper(i, {
                    observer: !0,
                    observeParents: !0,
                    loop: !0,
                    slidesPerView: 1,
                    spaceBetween: 0,
                    speed: 1500,
                    loopAdditionalSlides: 20,
                    navigation: {
                        nextEl: i.find(".nav__next"),
                        prevEl: i.find(".nav__prev")
                    },
                    autoplay: {
                        delay: 7e3,
                        disableOnInteraction: !1
                    },
                    on: {
                        init: function() {
                            n.letterAnimation(i)
                        },
                        slideChangeTransitionEnd: function() {
                            n.letterAnimation(i)
                        }
                    }
                })
            })
        },
        letterAnimation: function(n) {
            n.find(".swiper-slide .char").removeClass("opened");
            var i = n.find(".swiper-slide-active").find(".char");
            i.each(function(n) {
                var i = e(this);
                setTimeout(function() {
                    i.addClass("opened")
                },
                40 * n)
            })
        },
        navigation__mobile: function() {
            e(".persono_fn_mobilemenu .hamburger").on("click",
            function() {
                var n = e(this),
                i = e(".persono_fn_mobilemenu .mobilemenu");
                return n.hasClass("is-active") ? (n.removeClass("is-active"), i.removeClass("opened"), i.slideUp(500)) : (n.addClass("is-active"), i.addClass("opened"), i.slideDown(500)),
                !1
            })
        },
        navigation__closer: function() {
            var i = e(".persono_fn_wrapper"),
            t = e(".persono_fn_sidebar").find(".nav_trigger");
            e(".persono_fn_sidebar .nav__button").off().on("click",
            function() {
                return i.hasClass("sidebar-closed") ? (i.removeClass("sidebar-closed"), t.text(t.data("close"))) : (i.addClass("sidebar-closed"), t.text(t.data("open"))),
                setTimeout(function() {
                    n.portfolio__category_filter(),
                    n.service__masonry(),
                    n.popup__resize(),
                    n.slider__initalize()
                },
                500),
                !1
            })
        },
        navigation__resize: function() {
            var n = !1,
            i = e(".persono_fn_sidebar"),
            t = e(".persono_fn_content"),
            o = e(".shifter_wrapper"),
            s = i.data("max"),
            a = i.data("min"),
            r = e(".persono_popup .stopper"),
            d = e(".persono_fn_sidebar .nav__button"),
            l = e(".persono_fn_sidebar .width_indicator");
            o.on("mousedown",
            function(e) {
                n = !0,
                e.clientX,
                d.addClass("resize")
            }),
            e(document).on("mousemove",
            function(e) {
                var o = e.clientX; ! n || o > s || o < a || (i.css("width", o), t.css("padding-left", o), r.css("left", o), l.html(o + "px"))
            }).on("mouseup",
            function() {
                n = !1,
                d.removeClass("resize")
            })
        },
        contact__submit: function() {
            e(".contact_form .submit").off().on("click",
            function() {
                var n = e(this).closest(".contact_form"),
                i = n.find(".name").val(),
                t = n.find(".email").val(),
                o = n.find(".tel").val(),
                s = n.find(".message").val(),
                a = n.find(".success"),
                r = a.data("success"),
                d = n.data("email");
                return a.empty(),
                "" === i || "" === t || "" === s || "" === d ? n.find(".empty_notice").slideDown(500).delay(2e3).slideUp(500) : e.post("modal/contact.php", {
                    ajax_name: i,
                    ajax_email: t,
                    ajax_emailto: d,
                    ajax_tel: o,
                    ajax_message: s
                },
                function(e) {
                    a.append(e),
                    a.find(".contact_error").length ? a.slideDown(500).delay(2e3).slideUp(500) : (a.append("<span class='contact_success'>" + r + "</span>"), a.slideDown(500).delay(4e3).slideUp(500)),
                    "" === e && n[0].reset()
                }),
                !1
            })
        },
        portfolio__category_filter: function() {
            e().isotope && e(".section_portfolio").each(function() {
                var n = e(this),
                i = n.find(".list"),
                t = n.find(".portfolio_filter");
                i.isotope(),
                t.find("a").off().on("click",
                function() {
                    var o = e(this).attr("data-filter");
                    return i = n.find(".list"),
                    t.find("a").removeClass("current"),
                    e(this).addClass("current"),
                    i.isotope({
                        itemSelector: ".list_item",
                        filter: o,
                        animationOptions: {
                            duration: 750,
                            easing: "linear",
                            queue: !1
                        }
                    }),
                    !1
                })
            })
        },
        parallax: function() {
            if (e("#scene").length > 0) {
                var n = e("#scene").get(0);
                new Parallax(n, {
                    relativeInput: !0
                })
            }
        },
        service__masonry: function() {
            var n = e(".grid").isotope({
                itemSelector: ".grid-item",
                transitionDuration: 1e3,
                getSortData: {
                    weight: function(n) {
                        var i = e(n).find(".new_index").val();
                        return parseFloat(i.replace(/[\(\)]/g, ""))
                    }
                }
            });
            n.isotope({
                sortBy: "weight"
            }),
            n.isotope("updateSortData").isotope()
        },
        popup__open_by_index: function(i, t, o, s) {
            t = parseInt(t, 10);
            var a, r, d = i.find(".list_item:nth-child(" + t + ") .item"),
            l = e(".persono_popup"),
            c = i.find(".list_item").length,
            p = l.find(".popup_in");
            if (p.removeClass("opened"), o) {
                var _ = e(".persono_fn_sidebar"),
                f = d.offset().left,
                u = d.offset().top,
                m = e(document).scrollTop(),
                v = d.outerWidth(),
                h = d.outerHeight(),
                g = _.outerWidth(),
                x = d.find(".gallery_img").data("image");
                "blog" === s && (v = d.find(".img_holder").outerWidth(), h = d.find(".img_holder").outerHeight(), x = d.find(".blog_img").data("image")),
                ("none" === _.css("display") || e(".sidebar-closed").length) && (g = 0);
                n.topPositon = e(window).scrollTop(),
                e("body").addClass("modal-open").scrollTop(n.topPositon),
                p.html(""),
                l.css({
                    width: v + "px",
                    height: h + "px",
                    overflow: "hidden",
                    borderRadius: "5px",
                    top: u - m + "px",
                    left: f + "px",
                    transition: "none"
                }).addClass("opened"),
                l.find(".extra_img").css({
                    backgroundImage: "url(" + x + ")",
                    transform: "scale(1) translateY(0)",
                    transition: "none"
                }),
                setTimeout(function() {
                    l.css({
                        transition: "all 1500ms ease",
                        width: e(window).width() - g,
                        left: g,
                        top: 0,
                        bottom: 0,
                        height: "100%",
                        borderRadius: 0
                    })
                },
                500),
                a = 2e3,
                r = 0
            } else a = 0,
            r = 1e3;
            setTimeout(function() {
                var e, o, s, a;
                l.addClass("transition-end"),
                l.css({
                    transitionDuration: "500ms",
                    overflowY: "scroll"
                }),
                l.find(".extra_img").css({
                    transform: "translateY(-100%) scale(0.9)",
                    transition: "all 500ms ease"
                }),
                p.html(d.find(".hidden_info").html()),
                l.animate({
                    scrollTop: 0
                },
                r),
                setTimeout(function() {
                    p.addClass("opened")
                },
                r),
                (e = t + 1) > c && (e = 1),
                0 === (o = t - 1) && (o = c),
                s = i.find(".list_item:nth-child(" + o + ") .fn__title").text(),
                a = i.find(".list_item:nth-child(" + e + ") .fn__title").text(),
                l.find(".popup_nav .prev_post a").text(s).attr("title", s),
                l.find(".popup_nav .next_post a").text(a).attr("title", a),
                l.find(".popup_nav .prev_post input").val(o),
                l.find(".popup_nav .next_post input").val(e),
                n.popup__actions()
            },
            a)
        },
        service__open_by_index: function(i, t) {
            var o = i.find(".list li:nth-child(" + t + ") .item");
            if (i.hasClass("opened")) {
                if (o.hasClass("opened")) return ! 1;
                i.find(".item.opened").removeClass("opened")
            } else i.addClass("opened");
            o.addClass("opened");
            var s = i.find(".fn_inner"),
            a = i.find(".list li").length;
            s.addClass("ready").removeClass("opened").html("").html(o.find(".hidden_info").html());
            var r, d, l, c, p, _, f, u = o.closest("li");
            u.siblings().each(function() {
                var n = e(this);
                n.find(".new_index").val(n.find(".old_index").val() + 1)
            }),
            u.find(".new_index").val(1),
            (d = (r = parseInt(u.find(".old_index").val(), 10)) + 1) > a && (d = 1),
            0 === (l = r - 1) && (l = a),
            c = i.find(".list li:nth-child(" + l + ") .fn__title").text(),
            p = i.find(".list li:nth-child(" + d + ") .fn__title").text(),
            _ = i.find(".list li:nth-child(" + l + ") .counter").text(),
            f = i.find(".list li:nth-child(" + d + ") .counter").text(),
            i.find(".service_nav .prev a").text(_ + ". " + c),
            i.find(".service_nav .next a").text(f + ". " + p),
            i.find(".service_nav .prev input").val(l),
            i.find(".service_nav .next input").val(d),
            setTimeout(function() {
                s.addClass("opened")
            },
            100),
            n.service__masonry(),
            e([document.documentElement, document.body]).animate({
                scrollTop: i.offset().top - 50
            },
            1e3),
            n.service__actions(),
            n.title__animation()
        },
        service__actions: function() {
            e(".persono_services .service_nav a").off().on("click",
            function() {
                var i = e(this),
                t = parseInt(i.closest("li").find("input").val(), 10),
                o = i.closest(".persono_services");
                return n.service__open_by_index(o, t),
                !1
            }),
            e(".persono_services .item").off().on("click",
            function() {
                var i = e(this),
                t = i.closest(".persono_services"),
                o = parseInt(i.closest("li").find(".old_index").val(), 10);
                return n.service__open_by_index(t, o),
                !1
            }),
            e(".persono_services .fn__closer").off().on("click",
            function() {
                var i = e(this).closest(".persono_services");
                i.removeClass("opened").find(".opened").removeClass("opened"),
                i.find(".fn_inner").removeClass("ready"),
                i.find(".list li").each(function() {
                    var n = e(this);
                    n.find(".new_index").val(n.find("old_index").val())
                }),
                n.service__actions(),
                n.title__animation(),
                n.service__masonry()
            })
        },
        popup__actions: function() {
            e(".persono_popup .post_nav a").off().on("click",
            function() {
                var i = e(this),
                t = parseInt(i.siblings("input").val(), 10),
                o = e(".popup_added");
                return n.popup__open_by_index(o, t, !1),
                !1
            }),
            e(".portfolio_list .item").off().on("click",
            function() {
                var i = e(this),
                t = i.closest(".portfolio_list"),
                o = parseInt(i.closest("li").find(".index").val(), 10);
                return t.addClass("popup_added"),
                n.popup__open_by_index(t, o, !0),
                !1
            }),
            e(".section_blog .item").off().on("click",
            function() {
                var i = e(this),
                t = i.closest(".list"),
                o = parseInt(i.closest("li").find(".index").val(), 10);
                return t.addClass("popup_added"),
                n.popup__open_by_index(t, o, !0, "blog"),
                !1
            }),
            e(".persono_popup .fn__closer").off().on("click",
            function() {
                e(".persono_popup").removeClass("opened transition-end").attr("data-address", ""),
                e(".popup_added").removeClass("popup_added"),
                e("body").removeClass("modal-open"),
                e(window).scrollTop(n.topPositon)
            })
        },
        popup__resize: function() {
            var n = e(".persono_popup,.persono_popup .stopper");
            if (!n.hasClass("transition-end")) return ! 1;
            var i = e(".persono_fn_sidebar"),
            t = i.outerWidth(); ("none" === i.css("display") || e(".sidebar-closed").length) && (t = 0),
            n.css({
                left: t,
                width: "auto"
            })
        },
        anchor__jump: function() {
            e(".persono_fn_sidebar .navigation a,.persono_fn_mobilemenu ul a").off().on("click",
            function() {
                var n = e(this).attr("href");
                if ( - 1 !== n.indexOf("#") && e(n).length) {
                    var i = e(n).offset().top,
                    t = e(window).scrollTop(),
                    o = Math.abs(i - t) / 2;
                    return o = (o = o > 2e3 ? 2e3: o) < 300 ? 300 : o,
                    e([document.documentElement, document.body]).animate({
                        scrollTop: e(n).offset().top
                    },
                    o),
                    e(".persono_fn_mobilemenu .hamburger").removeClass("is-active"),
                    e(".persono_fn_mobilemenu .mobilemenu").removeClass("opened").slideUp(500),
                    !1
                }
            })
        },
        resume__hover: function() {
            e(".section_resume li").on("mouseenter",
            function() {
                var n = e(this),
                i = n.closest(".resume_footer"),
                t = i.find(".hover_box"),
                o = n.offset().top - i.offset().top,
                s = n.outerHeight();
                t.addClass("ready").css({
                    height: s + "px",
                    transform: "translateY(" + o + "px)"
                })
            }).on("mouseleave",
            function() {
                e(".section_resume .hover_box").removeClass("ready").css({
                    transform: "translateY(0px)"
                })
            })
        },
        navigation__scroll: function() {
            var n = e(".persono_fn_sidebar"),
            i = n.height(),
            t = n.find(".navigation ul"),
            o = n.find(".logo").outerHeight(),
            s = n.find(".copyright").outerHeight(),
            a = i - o - s,
            r = t.height();
            r < a && t.css({
                paddingTop: (a - r) / 2 + "px",
                paddingBottom: (a - r) / 2 + "px"
            }),
            n.find(".navigation").css({
                height: i - o - s + "px"
            }),
            e().niceScroll && n.find(".navigation").niceScroll({
                touchbehavior: !1,
                cursorwidth: 0,
                autohidemode: !0,
                cursorborder: "0px solid #333"
            }),
            t.addClass("ready")
        },
        title__animation: function() {
            e(".animated_text").each(function(i) {
                var t = e(this);
                if (!t.hasClass("js_ready")) {
                    var o = t.find(".text").text(),
                    s = "";
                    e.each(o.split(""),
                    function(e, n) {
                        0 === e && (s += '<span class="word">'),
                        s += " " === n ? '</span>&nbsp<span class="word">': '<span class="char">' + n + "</span>"
                    }),
                    t.find(".text").html(s).attr("data-title", o)
                }
                t.addClass("js_ready"),
                t.waypoint({
                    handler: function() {
                        n.animate__start(i, t)
                    },
                    offset: "85%"
                })
            })
        },
        getScrollbarWidth: function() {
            e("body").append("<style>body.modal-open{padding-right: " + (window.innerWidth - document.documentElement.clientWidth) + "px}</style>")
        },
        animate__stop: function(e, n) {
            n.removeClass("ready"),
            n.find(".char").removeClass("opened")
        },
        animate__start: function(n, i) {
            i.addClass("ready");
            var t = i.find(".char"),
            o = 0;
            t.each(function(n) {
                var i = e(this);
                o = 45 * n + 1500,
                setTimeout(function() {
                    i.addClass("opened")
                },
                o)
            }),
            setTimeout(function() {
                i.find(".text").text(i.find(".text").attr("data-title"))
            },
            o + 45 + 500)
        },
        img__to_svg: function() {
            e("img.persono_fn_svg").each(function() {
                var n = e(this),
                i = n.attr("class"),
                t = n.attr("src");
                e.get(t,
                function(t) {
                    var o = e(t).find("svg");
                    void 0 !== i && (o = o.attr("class", i + " replaced-svg")),
                    n.replaceWith(o)
                },
                "xml")
            })
        },
        background__image: function() {
            e("*[data-image]").each(function() {
                var n = e(this),
                i = n.attr("data-image"),
                t = n.data("image");
                void 0 !== i && n.css({
                    backgroundImage: "url(" + t + ")"
                })
            })
        }
    };
    e(document).ready(function() {
        n.init()
    }),
    e(window).on("resize",
    function() {
        n.navigation__scroll(),
        n.popup__resize(),
        n.portfolio__category_filter()
    }),
    e(window).on("load",
    function() {
        setTimeout(function() {
            n.portfolio__category_filter()
        },
        10)
    }),
    e(window).on("scroll",
    function() {})
} (jQuery);