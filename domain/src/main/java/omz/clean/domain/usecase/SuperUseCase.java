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

package omz.clean.domain.usecase;

import omz.clean.domain.utils.RXUtils;
import rx.Observable;
import rx.Subscription;

/**
 * Created by omrierez on 07/08/16.
 */

public abstract class SuperUseCase {


    private Subscription mSubscription;



    protected abstract Observable createObservable();

    public void unsubscribe() {
        RXUtils.safeUnsubscribe(mSubscription);
    }

}
