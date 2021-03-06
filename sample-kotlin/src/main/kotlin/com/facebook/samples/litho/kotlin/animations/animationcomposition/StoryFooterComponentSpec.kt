/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.samples.litho.kotlin.animations.animationcomposition

import android.graphics.Color
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Diff
import com.facebook.litho.Row
import com.facebook.litho.StateValue
import com.facebook.litho.Transition
import com.facebook.litho.Transition.TransitionUnitsBuilder
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.animation.DimensionValue
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnCreateTransition
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.OnUpdateState
import com.facebook.litho.annotations.State
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object StoryFooterComponentSpec {

  @OnCreateLayout
  fun onCreateLayout(c: ComponentContext, @State commentText: Boolean): Component =
      if (!commentText) {
        Row.create(c)
            .backgroundColor(Color.WHITE)
            .heightDip(56f)
            .child(
                Row.create(c)
                    .widthPercent(33.3f)
                    .alignItems(YogaAlign.CENTER)
                    .justifyContent(YogaJustify.CENTER)
                    .clickHandler(StoryFooterComponent.onClick(c))
                    .testKey("like_button")
                    .child(
                        Column.create(c)
                            .heightDip(24f)
                            .widthDip(24f)
                            .backgroundColor(Color.RED)
                            .transitionKey("icon_like"))
                            .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                    .child(
                        Text.create(c)
                            .textSizeSp(16f)
                            .text("Like")
                            .transitionKey("text_like")
                            .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                            .marginDip(YogaEdge.LEFT, 8f)))
            .child(
                Row.create(c)
                    .transitionKey("cont_comment")
                    .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                    .widthPercent(33.3f)
                    .alignItems(YogaAlign.CENTER)
                    .justifyContent(YogaJustify.CENTER)
                    .child(Column.create(c).heightDip(24f).widthDip(24f).backgroundColor(Color.RED))
                    .child(
                        Text.create(c).textSizeSp(16f).text("Comment").marginDip(YogaEdge.LEFT,
                            8f)))
            .child(
                Row.create(c)
                    .widthPercent(33.3f)
                    .alignItems(YogaAlign.CENTER)
                    .justifyContent(YogaJustify.CENTER)
                    .child(
                        Column.create(c)
                            .transitionKey("icon_share")
                            .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                            .heightDip(24f)
                            .widthDip(24f)
                            .backgroundColor(Color.RED))
                    .child(
                        Text.create(c)
                            .textSizeSp(16f)
                            .text("Share")
                            .transitionKey("text_share")
                            .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                            .marginDip(YogaEdge.LEFT, 8f)))
            .build()
      } else {
        Row.create(c)
            .backgroundColor(Color.WHITE)
            .heightDip(56f)
            .child(
                Row.create(c)
                    .alignItems(YogaAlign.CENTER)
                    .justifyContent(YogaJustify.CENTER)
                    .clickHandler(StoryFooterComponent.onClick(c))
                    .paddingDip(YogaEdge.HORIZONTAL, 16f)
                    .testKey("like_button")
                    .child(
                        Column.create(c)
                            .transitionKey("icon_like")
                            .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                            .heightDip(24f)
                            .widthDip(24f)
                            .backgroundColor(Color.RED)))
            .child(
                Column.create(c)
                    .flexGrow(1f)
                    .transitionKey("comment_editText")
                    .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                    .child(Text.create(c).text("Input here").textSizeSp(16f)))
            .child(
                Row.create(c)
                    .transitionKey("cont_share")
                    .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                    .alignItems(YogaAlign.CENTER)
                    .clickHandler(StoryFooterComponent.onClick(c))
                    .paddingDip(YogaEdge.ALL, 16f)
                    .backgroundColor(0xFF0000FF.toInt())
                    .child(
                        Column.create(c)
                            .transitionKey("icon_share")
                            .transitionKeyType(Transition.TransitionKeyType.GLOBAL)
                            .heightDip(24f)
                            .widthDip(24f)
                            .backgroundColor(Color.RED)))
            .build()
      }

  @OnEvent(ClickEvent::class)
  fun onClick(c: ComponentContext) {
    StoryFooterComponent.updateState(c)
  }

  @OnUpdateState
  fun updateState(commentText: StateValue<Boolean>) {
    commentText.set(commentText.get() != true)
  }

  @OnCreateTransition
  fun onCreateTransition(c: ComponentContext, @State commentText: Diff<Boolean>): Transition? =
      if (commentText.previous == null) {
        null
      } else {
        Transition.parallel<TransitionUnitsBuilder>(
            Transition.create(Transition.TransitionKeyType.GLOBAL, "comment_editText")
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0f)
                .disappearTo(0f)
                .animate(AnimatedProperties.X)
                .appearFrom(DimensionValue.widthPercentageOffset(-50f))
                .disappearTo(DimensionValue.widthPercentageOffset(-50f)),
            Transition.create(Transition.TransitionKeyType.GLOBAL, "cont_comment")
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0f)
                .disappearTo(0f),
            Transition.create(Transition.TransitionKeyType.GLOBAL, "icon_like", "icon_share").animate(AnimatedProperties.X),
            Transition.create(Transition.TransitionKeyType.GLOBAL, "text_like", "text_share")
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0f)
                .disappearTo(0f)
                .animate(AnimatedProperties.X)
                .appearFrom(DimensionValue.widthPercentageOffset(50f))
                .disappearTo(DimensionValue.widthPercentageOffset(50f)))
      }
}
