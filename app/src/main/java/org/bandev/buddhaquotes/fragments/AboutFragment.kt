/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.bandev.buddhaquotes.BuildConfig
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.AboutAdapter
import org.bandev.buddhaquotes.core.AnimationUtils
import org.bandev.buddhaquotes.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.aboutAppVersion.text = BuildConfig.VERSION_NAME

        with(binding.contributorsPeople) {
            layoutManager = LinearLayoutManager(context)
            adapter = AboutAdapter(resources.getStringArray(R.array.contributors_people))
            setHasFixedSize(true)
        }

        with(binding.promises) {
            layoutManager = LinearLayoutManager(context)
            adapter = AboutAdapter(resources.getStringArray(R.array.app_promises))
            setHasFixedSize(true)
        }

        binding.titleCard.setOnClickListener {
            expandTitleCard()
        }
        binding.contributorsCard.setOnClickListener {
            expandContributorCard()
        }
        binding.promiseCard.setOnClickListener {
            expandPromiseCard()
        }
    }

    private fun expandTitleCard() {
        val visible = binding.titleCardExpandable.visibility != View.VISIBLE
        if (visible) AnimationUtils.expand(binding.titleCardExpandable)
        else AnimationUtils.collapse(binding.titleCardExpandable)
    }

    private fun expandContributorCard() {
        val visible = binding.contributorsCardExpandable.visibility != View.VISIBLE
        if (visible) AnimationUtils.expand(binding.contributorsCardExpandable)
        else AnimationUtils.collapse(binding.contributorsCardExpandable)
    }

    private fun expandPromiseCard() {
        val visible = binding.promiseCardExpandable.visibility != View.VISIBLE
        if (visible) AnimationUtils.expand(binding.promiseCardExpandable)
        else AnimationUtils.collapse(binding.promiseCardExpandable)
    }
}
