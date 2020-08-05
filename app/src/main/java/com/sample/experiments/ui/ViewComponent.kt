package com.sample.experiments.ui

import androidx.fragment.app.Fragment

interface ViewComponent {
    val type : String
}

interface FragmentViewComponent : ViewComponent{
    val fragment : Fragment
}