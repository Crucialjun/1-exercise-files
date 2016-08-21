/*
 *
 * Copyright (C) 2016. OMRI EREZ
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

package omz.clean.domain.rx;

import java.lang.ref.WeakReference;

/**
 * Created by omrierez on 07/08/16.
 */

public class WeakSubscriber {

    /**
     * We are using a WeakReference here because the {@link rx.Subscriber}s are known
     * as a potential cause of memory leaks in an application where components have a finite life cycle.
     * In our case: The Android operating system controls the application's components life cycle.
     */
    WeakReference<SuperSubscriber> mWeakSubscriber;

    public WeakSubscriber(SuperSubscriber subscriber) {
        this.mWeakSubscriber = new WeakReference<>(subscriber);
    }

    public SuperSubscriber get() {
        final SuperSubscriber sub = mWeakSubscriber.get();
        if (sub == null)
            System.out.print("INFO : A weak reference was empty when tried to be accessed");
        return sub;
    }
}
