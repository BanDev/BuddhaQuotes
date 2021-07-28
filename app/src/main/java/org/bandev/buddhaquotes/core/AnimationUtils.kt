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

package org.bandev.buddhaquotes.core

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

object AnimationUtils {
    fun expand(viewBeingAnimated: View) {
        //Measure full height of the view. In our case, it's the taskDescription text view on our CardView.
        viewBeingAnimated.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        //This is the final height that the expand animation is aiming for.
        val targetHeight: Int = viewBeingAnimated.measuredHeight

        //Setup the view to expand.
        viewBeingAnimated.layoutParams.height = 1
        viewBeingAnimated.visibility = View.VISIBLE

        val expandAnimation: Animation = object : Animation() {
            //Called on every frame of the animation
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                //Grow the view's height over time. And if the animation is over, make sure the view is at full height.
                viewBeingAnimated.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                //Redraw layout
                viewBeingAnimated.requestLayout()
            }

            //This tells Android that this animation will change the boundaries of its view.
            override fun willChangeBounds() = true
        }

        //This ensures the duration of the animation is appropriate for the size of the view being expanded.
        //In other words, you'll have a longer expand animation for a tall view and vice versa.
        expandAnimation.duration = (targetHeight / getViewDensity(viewBeingAnimated)).toLong()
        viewBeingAnimated.startAnimation(expandAnimation)
    }

    fun collapse(viewBeingAnimated: View) {
        //The initial height is the starting point for our animation.
        val initialHeight: Int = viewBeingAnimated.measuredHeight

        val collapseAnimation: Animation = object : Animation() {
            //Called on every frame of the animation
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                //If the animation is over, this cleans up the view from the layout completely
                if (interpolatedTime == 1f)
                    viewBeingAnimated.visibility = View.GONE
                else {
                    //Shrink the view's height over time.
                    viewBeingAnimated.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()

                    //Redraw layout
                    viewBeingAnimated.requestLayout()
                }
            }

            //This animation will change the boundaries of its view.
            override fun willChangeBounds() = true
        }

        //Once again, the duration is relative to the initial height of the view.
        //It's also inversely proportional to the density of the view.
        collapseAnimation.duration = (initialHeight / getViewDensity(viewBeingAnimated)).toLong()
        viewBeingAnimated.startAnimation(collapseAnimation)
    }

    //This function returns the relative density of the view.
    //Screens with more pixels will make this value larger.
    //See https://developer.android.com/training/multiscreen/screendensities for reference.
    private fun getViewDensity(view: View) = view.context.resources.displayMetrics.density
} 